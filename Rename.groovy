class Rename implements Table {
    final Table table
    final Map<String,String> reMap
    final List<String> columns
    
    Rename(final Table table, final Closure closure) {
        this.table = table
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        WasCalled wc = new WasCalled()
        closure.setDelegate(wc)
        closure.call()
        Map<String,String> tmp = originalMap(table.columns)
        tmp.putAll(wc.asMap())
        this.reMap = tmp.inject([:]) { nm, k, v -> nm[v] = k; nm; }
        this.columns = reMap.inject([]) { l, k, v -> l << k }
    }

    private Map<String,String> originalMap(final List<String> columns) {
        return table.columns.collectEntries { col -> new MapEntry(col, col) }
    }

    private List<String> newColumns(final List<String> columns, final Map<String,String> reMap) {
        List<String> ret = []
        columns.each { col -> ret.add(reMap[col]) }
        return ret
    }
    
    List<String> getColumns() {
        return columns
    }

    Iterator<Row> iterator() {
        return new Iter(this)
    }

    static class MyRow implements Row {
        final Rename rename
        final Row original

        MyRow(final Rename rename, final Row original) {
            this.rename = rename
            this.original = original
        }

        List<String> getColumns() {
            return rename.columns
        }
        
        Object get(String col) {
            String originalColumn = rename.reMap[col]
            return original.get(originalColumn)
        }
        
        List<?> getAll() {
            return original.all
        }
    }

    static class Iter implements Iterator<Row> {
        final Rename rename
        final Iterator<Row> iter
        
        Iter(final Rename rename) {
            this.rename = rename
            this.iter = rename.table.iterator()
        }

        boolean hasNext() {
            return iter.hasNext()
        }

        Row next() {
            return new MyRow(rename, iter.next())
        }

        void remove() {
            throw new UnsupportedOperationException()
        }
    }
}
