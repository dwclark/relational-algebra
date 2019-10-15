class GroupBy extends RowTable {
    GroupBy(final Table table, Closure fields, Map<String,Closure> aggregates) {
        super(_columns(fields, aggregates), _grouping(table, fields, aggregates))
    }

    private static List<String> _columns(final Closure fields,
                                         final Map<String,Closure> aggregates) {
        return [ _groupFields(fields), aggregates.keySet() ].flatten()
    }

    private static List<String> _groupFields(Closure fields) {
        final WasCalled wc = new WasCalled()
        fields.resolveStrategy = Closure.DELEGATE_FIRST
        fields.setDelegate(wc)
        fields.call()
        return wc.order
    }
    
    private static List<List<?>> _grouping(final Table table, final Closure fields,
                                           final Map<String,Closure> aggregates) {
        Map<Group,List<Row>> grouped = _grouped(table, _groupFields(fields))
    }

    private static Map<Group,List<Row>> _grouped(final Table table, final List<String> groupFields) {
        table.inject([:]) { map, row ->
            final Group g = new Group(groupFields.inject([]) { list, field -> list << row.get(field) }) 
            map.get(g, []).add(row)
            map
        }
    }

    private static List<List<?>> _compute(final Map<Group,List<Row>> grouped,
                                          final Map<String,Closure> aggregates) {
        grouped.inject([]) { retList, group, rows ->
            retList << aggregates.inject(new ArrayList(group.values)) { rowList, closure ->
                rowList << closure.call(rows)
            }
        }
    }

    private class Group {
        List<?> values = []

        Group(final Row row) {
            values = groupFields.inject([]) { list, field -> row.get(field) }
        }

        int hashCode() {
            return values.hashCode()
        }

        boolean equals(final Object o) {
            return values.equals(o.values)
        }
    }
}
