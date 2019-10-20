class OuterJoin extends RowTable {

    OuterJoin(final Table left, final Table right, final Closure<Row> criteria) {
        super(left.columns + right.columns, doData(left, right, criteria))
    }

    private static List<List<?>> doData(final Table left, final Table right, final Closure<Row> criteria) {
        List<List<?>> tmp = new Join(left, right, criteria).collect { Row r -> r.all }
        final int total = left.columns.size() + right.columns.size()
        addMissing(left, 0, left.columns.size(), total, tmp)
        addMissing(right, left.columns.size(), total, total, tmp)
        return tmp
    }

    private static void addMissing(final Table table, final int low, final int high,
                                   final int total, final List<List<?>> tmp) {
        final ArrayList<?> toCmp = new ArrayList<>(high - low)
        final Set<List<?>> set = tmp.collect { List<?> list -> list[low..<high] } as Set
        table.each { Row row ->
            List<?> list = row.all
            if(!set.contains(list)) {
                int copyIndex = 0
                final List<?> toAdd = new ArrayList<>(total)
                for(int i = 0; i < total; ++i) {
                    if(low <= i && i < high) {
                        toAdd.add(list[copyIndex++])
                    }
                    else {
                        toAdd.add(null)
                    }
                }

                tmp.add(toAdd)
            }
        }
    }
}
