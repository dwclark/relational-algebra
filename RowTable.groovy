class RowTable implements Table {
    final List<String> columns
    final List<List<?>> rows
    final Map<String,Integer> columnMap

    RowTable(List<String> columns) {
        this(columns, new ArrayList<>())
    }
    
    RowTable(List<String> columns, List<List<?>> rows) {
        this.columns = columns
        this.rows = rows
        this.columnMap = populateColumnMap(columns)
    }

    private Map<String,Integer> populateColumnMap(final List<String> columns) {
        final Map<String,Integer> ret = [:];
        columns.eachWithIndex { col, idx -> ret[col] = idx; }
        return ret;
    }

    public void addRow(final List<?> list) {
        rows.add(list)
    }

    public Iterator<Row> iterator() {
        return new Iter(this)
    }

    static class MyRow implements Row {
        private final RowTable table
        private final int index
        
        public MyRow(final RowTable table, final int index) {
            this.table = table
            this.index = index
        }
        
        Object get(final String col) {
            final int columnIndex = table.columnMap[col]
            return table.rows[index][columnIndex]
        }

        List<?> getAll() {
            return table.rows[index]
        }

        List<String> getColumns() {
            return table.columns
        }
    }

    private static class Iter implements Iterator<Row> {
        private RowTable table
        private int index
        
        public Iter(final RowTable table) {
            this.table = table
        }

        public boolean hasNext() {
            return index < table.rows.size()
        }

        public Row next() {
            return new MyRow(table, index++)
        }

        public void remove() {
            throw new UnsupportedOperationException()
        }
    }
}
