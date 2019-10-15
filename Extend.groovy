class Extend implements Table {

    final Table original
    final Map<String,Closure> reMap
    final List<String> columns
    
    Extend(final Table original, final Map<String,Closure> reMap) {
        this.original = original
        this.reMap = reMap

        List<String> tmp = new ArrayList(original.columns)
        tmp.addAll(reMap.keySet())
        this.columns = tmp
    }

    private static class Iter implements Iterator<Row> {
        final Iterator<Row> iter
        final Extend extend
        
        Iter(final Iterator<Row> iter, final Extend extend) {
            this.iter = iter
        }

        boolean hasNext() {
            return iter.hasNext()
        }

        Row next() {
            return new MyRow(iter.next(), extend)
        }

        void remove() {
            throw new UnsupportedOperationException()
        }
    }

    private static class MyRow implements Row {
        final Row _row
        final Extend _extend
        
        MyRow(final Row row, final Extend extend) {
            _row = row
            _extend = extend
        }
        
        List<String> getColumns() {
            return _extend.columns
        }
        
        Object get(final String col) {
            if(_extend.reMap.containsKey(col)) {
                final Closure closure = _extend.reMap[col]
                closure.resolveStrategy = Closure.DELEGATE_FIRST
                closure.setDelegate(_row)
                return closure.call()
            }
            else {
                return _row.get(col)
            }
        }
        
        List<?> getAll() {
            List<?> ret = new ArrayList<>(_columns.size())
            _columns.each { col -> ret.add(get(col)) }
            return ret
        }
    }
}
