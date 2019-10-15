class WasCalled {
    List<String> order = []
    
    String get(String s) {
        order.add(s)
    }

    Map<String,String> asMap() {
        Map<String,String> ret = [:]
        for(int i = 0; i < order.size(); i += 2) {
            ret[order[i]] = order[i+1]
        }

        return ret
    }

    Map<String,String> asMapSwapped() {
        Map<String,String> ret = [:]
        for(int i = 0; i < order.size(); i += 2) {
            ret[order[i+1]] = order[i]
        }

        return ret
    }
}
