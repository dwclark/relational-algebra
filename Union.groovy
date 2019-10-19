class Union implements Table {
    final List<String> columns
    final Set<Row> rows
    
    Union(final Table left, final Table right) {
        if(left.columns != right.columns) {
            throw new UnsupportedOperationException()
        }

        this.columns = left.columns
        this.rows = new HashSet<>()
        left.each { row -> rows.add(row) }
        right.each { row -> rows.add(row) }
    }

    Iterator<Row> iterator() {
        return rows.iterator()
    }
}
