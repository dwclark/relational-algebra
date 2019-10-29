class Project extends InMemoryTable {
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
        return new ForwardIter(table.iterator(), { Row r -> new _Row(r, this) })
    }

    private static class _Row extends ForwardRow {
        final Project project
        
        _Row(Row row, Project project) {
            super(row, project)
            this.project = project
        }

        public Object get(final String col) {
            if(project.columns.contains(col)) {
                return row.get(col)
            }
            else {
                throw new UnsupportedOperationException()
            }
        }
    }
}
