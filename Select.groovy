class Select implements Table {
    final Table table
    final Closure criteria
    
    Select(final Table table, final Closure<Row> criteria) {
        this.table = table;
        this.criteria = criteria
        this.criteria.resolveStrategy = Closure.DELEGATE_FIRST
    }

    List<String> getColumns() {
        return table.columns
    }
    
    Iterator<Row> iterator() {
        return new Iter(this)
    }

    private static class Iter implements Iterator<Row> {
        final Select select
        final Iterator<Row> _iter
        Row _row
        
        Iter(final Select select) {
            this.select = select
            _iter = select.table.iterator()
            findNext()
        }

        public boolean hasNext() {
            return _row != null
        }

        public Row next() {
            Row ret = _row
            findNext()
            return ret
        }

        public void remove() {
            throw new UnsupportedOperationException()
        }
        
        private void findNext() {
            while(_iter.hasNext()) {
                Row tmp = _iter.next()
                select.criteria.setDelegate(tmp)
                if(select.criteria.call()) {
                    _row = tmp
                    return;
                }
            }

            _row = null
        }
    }
}
