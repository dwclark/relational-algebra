class SemiJoin extends GroupBy {

    SemiJoin(final Table left, final Table right, final Closure criteria) {
        super(new Join(left, right, criteria), left.columns, Collections.emptyMap())
    }
}
