trait Table implements Iterable<Row> {
    abstract List<String> getColumns()
    abstract Iterator<Row> iterator()

    @Override
    String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator())
        sb.append(columns.join(',')).append(System.lineSeparator())
        each { r -> sb.append(r.all.join(',')).append(System.lineSeparator()) }
        return sb.toString()
    }
}
