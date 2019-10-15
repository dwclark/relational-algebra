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

    private Object _first(List<Row> rows, final String field) {
        def firstRow = rows.find { row -> row.get(field) != null }
        if(firstRow == null) return null
        else return firstRow.get(field)
    }

    Closure min(final String field) {
        return { List<Row> rows ->
            def theMin = _first(rows, field)
            if(theMin == null) return theMin
            
            rows.each { row ->
                def val = row.get(field)
                if(val != null && val < theMin) theMin = val
            }

            return theMin
        }
    }

    Closure max(final String field) {
        return { List<Row> rows ->
            def theMax = _first(rows, field)
            if(theMax == null) return theMax
            
            rows.each { row ->
                def val = row.get(field)
                if(val != null && val > theMax) theMax = val
            }

            return theMax
        }
    }

    Closure avg(final String field) {
        return { List<Row> rows -> sum(field).call() / count(field).call() }
    }
}
