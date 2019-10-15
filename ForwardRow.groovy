abstract class ForwardRow<T extends Table> implements Row {
    final Row row
    final T table

    ForwardRow(final Row row, final T table) {
        this.row = row
        this.table = table
    }

    List<String> getColumns() {
        return table.columns
    }

    List<?> getAll() {
        return columns.inject(new ArrayList<>(columns.size())) { ret, col -> ret << get(col) }
    }
}
