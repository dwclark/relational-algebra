class Aggregates {

    String get(final String s) {
        return s
    }

    Closure sum(final String field) {
        return { List<Row> rows ->
            rows.inject(0) { tot, row ->
                def val = row.get(field)
                tot += (val ?: 0)
            }
        }
    }

    Closure count(final String field) {
        return { List<Row> rows ->
            rows.inject(0) { tot, row ->
                tot += row.get(field) ? 1 : 0
            }
        }
    }

    final static Comparator<Row> cmp = { Row row1, Row row2 ->
        def a = row1.get(field)
        def b = row2.get(field)
        
        if(a == null && b == null) return 0
        else if(a == null) return -1
        else if(b == null) return 1
        else return a <=> b
    }
    
    Closure min(final String field) {
        return { List<Row> rows -> rows.min(cmp) }
    }

    Closure max(final String field) {
        return { List<Row> rows -> rows.max(cmp) }
    }

    Closure avg(final String field) {
        return { List<Row> rows -> sum(field).call() / count(field).call() }
    }
}
