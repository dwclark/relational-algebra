class Rename implements Table {
    
    final Table original
    final Map<String,String> reMap
    final List<String> columns
    
    Rename(final Table original, final Closure closure) {
        this.original = original
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        WasCalled wc = new WasCalled()
        closure.setDelegate(wc)
        closure.call()
        Map<String,String> tmp = originalMap(original.columns)
        tmp.putAll(wc.asMap())
        this.reMap = tmp.inject([:]) { nm, k, v -> nm[v] = k; nm; }
        this.columns = reMap.inject([]) { l, k, v -> l << k }
    }

    private Map<String,String> originalMap(final List<String> columns) {
        return original.columns.collectEntries { col -> new MapEntry(col, col) }
    }

    private List<String> newColumns(final List<String> columns, final Map<String,String> reMap) {
        List<String> ret = []
        columns.each { col -> ret.add(reMap[col]) }
        return ret
    }

    Iterator<Row> iterator() {
        println "In iterator"
        return new ForwardIter(original.iterator(), this)
    }

    Row wrapRow(final Row row) {
        return new _Row(row, this)
    }

    private static class _Row extends ForwardRow<Rename> {
        _Row(final Row row, final Rename rename) {
            super(row, rename)
        }
        
        Object get(final String col) {
            return row.get(table.reMap[col])
        }
    }
}
