class EmptyIter implements Iterator<Row> {
    final static Iterator<Row> instance = new EmptyIter()
    boolean hasNext() { return false }
    Row next() { throw new NoSuchElementException()}
}
