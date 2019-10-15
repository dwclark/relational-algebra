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

    Row wrapRow(final Row row) {
        return new MyRow(row, this)
    }

    Iterator<Row> iterator() {
        new ForwardIter(original.iterator(), this)
    }

    private static class MyRow extends ForwardRow<Extend> {
        MyRow(final Row row, final Extend extend) {
            super(row, extend)
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
