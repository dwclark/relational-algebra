import static Constants.*

class Sort extends RowTable {

    Sort(final Table table, final Closure closure) {
        super(table.columns, doSort(table, closure))
    }

    private static _sort(Row row1, Row row2, Map.Entry entry) {
        String column = entry.key
        String order = entry.value
        return (order == asc
                ? row1[column] <=> row2[column]
                : row2[column] <=> row1[column])
    }
    
    private static doSort(final Table table, final Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        SortWasCalled wc = new SortWasCalled()
        closure.setDelegate(wc)
        closure.call()

        List<Row> list = []
        table.each { r -> list.add(r) }
        list.sort { firstRow, secondRow ->
            for(int i = 0; i < wc.sorting.size() - 1; ++i) {
                int result = _sort(firstRow, secondRow, wc.sorting[i])
                if(result != 0) return result
            }

            return _sort(firstRow, secondRow, wc.sorting[wc.sorting.size() - 1])
        }

        return list.collect { it.all }
    }
}

public class SortWasCalled {
    final List sorting = []
    
    String get(String name) {
        sorting.add(new MapEntry(name, asc))
    }
    
    def methodMissing(String name, args) {
        sorting.add(new MapEntry(name, args[0]))
    }
}
