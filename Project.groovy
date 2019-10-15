class Project implements Table {
    final Table table
    final List<String> columns
    
    Project(final Table table, final Closure criteria) {
        this.table = table
        this.columns = []
        this.columns = extract(criteria)
    }

    private List<String> extract(final Closure criteria) {
        criteria.resolveStrategy = Closure.DELEGATE_FIRST
        WasCalled wc = new WasCalled()
        criteria.setDelegate(wc)
        criteria.call()
        return wc.order
    }
    
    List<String> getColumns() {
        return columns
    }
    
    Iterator<Row> iterator() {
        return new Iter(table.iterator())
    }

    private class Iter implements Iterator<Row> {
        final Iterator<Row> iter

        Iter(final Iterator<Row> iter) {1
            this.iter = iter
        }

        boolean hasNext() {
            return iter.hasNext()
        }

        Row next() {
            return new MyRow(iter.next(), columns)
        }

        void remove() {
            throw new UnsupportedOperationException()
        }
    }
    
    private static class MyRow implements Row {
        final Row _row
        final List<String> _columns
        
        MyRow(final Row row, final List<String> columns) {
            _row = row
            _columns = columns
        }
        
        List<String> getColumns() {
            return _columns
        }
        
        Object get(String col) {
            if(_columns.contains(col)) {
                return _row.get(col)
            }
            else {
                throw new UnsupportedOperationException()
            }
        }
        
        List<?> getAll() {
            List<?> ret = new ArrayList<>(_columns.size())
            _columns.each { col -> ret.add(_row.get(col)) }
            return ret
        }
    }
}
