interface Row {
    List<String> getColumns()
    Object get(String col)
    List<?> getAll()
}
