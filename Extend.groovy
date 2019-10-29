class Extend extends InMemoryTable {

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

    Iterator<Row> iterator() {
        new ForwardIter(original.iterator(), { Row r -> new _Row(r, this)})
    }
    
    private static class _Row extends ForwardRow {
        final Extend table
        
        _Row(final Row row, final Extend extend) {
            super(row, extend)
            table = extend
        }
        
        Object get(final String col) {
            if(table.reMap.containsKey(col)) {
                return table.reMap[col].call(row)
            }
            else {
                return row.get(col)
            }
        }
    }
}
