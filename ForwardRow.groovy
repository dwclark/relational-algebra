abstract class ForwardRow implements Row {
    final Row row
    final Table table

    ForwardRow(final Row row, final Table table) {
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
