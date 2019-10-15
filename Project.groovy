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
    
    Iterator<Row> iterator() {
        return new ForwardIter(table.iterator(), this)
    }

    private static class MyRow extends ForwardRow<Project> {
        
        MyRow(final Row row, final Project projection) {
            super(row, projection)
        }
        
        Object get(String col) {
            if(_columns.contains(col)) {
                return _row.get(col)
            }
            else {
                throw new UnsupportedOperationException()
            }
        }
    }
}
