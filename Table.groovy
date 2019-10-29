interface Table extends Iterable<Row> {
    List<String> getColumns()
    Iterator<Row> iterator()
    Table select(Closure criteria)
    Table project(Closure criteria)
    Table sort(Closure criteria)
    Table rename(Closure criteria)
    Table extend(Map<String,Closure> reMap)
    Table groupBy(Closure fields, Map<String,Closure> aggregates)

    Table product(Table right)
    Table join(Table right, Closure criteria)
    Table semiJoin(Table right, Closure criteria)
    Table antiJoin(Table right, Closure criteria)
    Table union(Table right)
    Table outerJoin(Table right, Closure criteria)
}
