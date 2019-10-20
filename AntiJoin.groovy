class AntiJoin extends RowTable {

    AntiJoin(final Table left, final Table right, final Closure<Row> criteria) {
        super(left.columns, doAntiJoin(left, right, criteria))
    }

    private static List<List<?>> doAntiJoin(final Table left, final Table right, final Closure<Row> criteria) {
        List<List<?>> ret = []
        final Combined combined = new Combined(criteria)
        left.each { Row leftRow ->
            if(!matches(leftRow, right, combined)) {
                ret << leftRow.all
            }
        }

        return ret
    }

    private static boolean matches(final Row leftRow, final Table right, final Combined combined) {
        final Iterator iter = right.iterator()
        
        while(iter.hasNext()) {
            if(combined.matches(leftRow, iter.next())) {
                return true
            }
        }

        return false
    }

    private static class Combined implements Row {
        final Closure<Row> criteria
        Row left
        Row right

        Combined(final Closure<Row> criteria){
            this.criteria = criteria
            criteria.resolveStrategy = Closure.DELEGATE_FIRST
            criteria.setDelegate(this)
        }

        public boolean matches(final Row left, final Row right) {
            this.left = left
            this.right = right
            return criteria.call()
        }

        List<String> getColumns() {
            return left.columns + right.columns
        }

        List<?> getAll() {
            return left.all + right.all
        }

        Object get(final String col) {
            return left.columns.contains(col) ? left.get(col) : right.get(col)
        }
    }
}
