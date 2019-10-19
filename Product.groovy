class Product implements Table {
    final Table left
    final Table right
    final List<String> columns

    Product(final Table left, final Table right) {
        this.left = left
        this.right = right
        this.columns = left.columns + right.columns
    }

    Iterator<Row> iterator() {
        return new Iter()
    }

    private class Iter implements Iterator<Row> {
        private Iterator leftIter
        private Iterator rightIter
        private Row leftCurrent
        
        Iter() {
            leftIter = left.iterator()
            rightIter = right.iterator()
            
            if(leftIter.hasNext() && rightIter.hasNext()) {
                leftCurrent = leftIter.next();
            }
            else {
                leftIter = EmptyIter.instance
                rightIter = EmptyIter.instance
                leftCurrent = null
            }
        }

        boolean hasNext() {
            rightIter.hasNext() || leftIter.hasNext()
        }

        Row next() {
            if(rightIter.hasNext()) {
                return new Combined(leftCurrent, rightIter.next())
            }

            if(leftIter.hasNext()) {
                leftCurrent = leftIter.next()
                rightIter = right.iterator()
                return new Combined(leftCurrent, rightIter.next())
            }

            throw new NoSuchElementException()
        }
    }

    private static class Combined implements Row {
        final Row left
        final Row right

        public Combined(final Row left, final Row right) {
            this.left = left
            this.right = right
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
