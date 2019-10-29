class ForwardIter implements Iterator<Row> {
    final Iterator<Row> iter
    final Closure<Row> wrapper;
    
    ForwardIter(final Iterator<Row> iter, final Closure<Row> wrapper) {
        this.iter = iter
        this.wrapper = wrapper
    }
    
    boolean hasNext() {
        return iter.hasNext()
    }
    
    Row next() {
        return wrapper.call(iter.next())
    }
    
    void remove() {
        throw new UnsupportedOperationException()
    }
}
