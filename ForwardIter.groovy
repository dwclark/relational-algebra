class ForwardIter implements Iterator<Row> {
    final Iterator<Row> iter
    final Table table
    
    ForwardIter(final Iterator<Row> iter, final Table table) {
        this.iter = iter
        this.table = table
    }
    
    boolean hasNext() {
        return iter.hasNext()
    }
    
    Row next() {
        return table.wrapRow(iter.next())
    }
    
    void remove() {
        throw new UnsupportedOperationException()
    }
}
