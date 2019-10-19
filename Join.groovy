class Join extends Select {
    Join(final Table left, final Table right, final Closure<Row> criteria) {
        super(new Product(left, right), criteria)
    }
}
