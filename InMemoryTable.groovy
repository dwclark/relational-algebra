abstract class InMemoryTable implements Table {

    @Override
    String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator())
        sb.append(columns.join(',')).append(System.lineSeparator())
        each { r -> sb.append(r.all.join(',')).append(System.lineSeparator()) }
        return sb.toString()
    }

    Table select(Closure criteria) {
        return new Select(this, criteria)
    }
    
    Table project(Closure criteria) {
        return new Project(this, criteria)
    }
    
    Table sort(Closure criteria) {
        return new Sort(this, criteria)
    }
    
    Table rename(Closure criteria) {
        return new Rename(this, criteria)
    }
    
    Table extend(Map<String,Closure> reMap) {
        return new Extend(this, reMap)
    }
    
    Table groupBy(Closure fields, Map<String,Closure> aggregates) {
        return new GroupBy(this, fields, aggregates)
    }

    Table product(Table right) {
        return new Product(this, right)
    }
    
    Table join(Table right, Closure criteria) {
        return new Join(this, right, criteria)
    }
    
    Table semiJoin(Table right, Closure criteria) {
        return new Join(this, right, criteria)
    }
    
    Table antiJoin(Table right, Closure criteria) {
        return new AntiJoin(this, right, criteria)
    }
    
    Table union(Table right) {
        return new Union(this, right)
    }
    
    Table outerJoin(Table right, Closure criteria) {
        return new OuterJoin(this, right, criteria)
    }
}
