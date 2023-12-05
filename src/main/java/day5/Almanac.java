package day5;

import java.util.ArrayList;
import java.util.List;

class Almanac {

    private final List<AlmanacMap> almanacMaps = new ArrayList<>();

    void addMap(AlmanacMap almanacMap) {
        almanacMaps.add(almanacMap);
    }

    AlmanacMap getLast() {
        return almanacMaps.getLast();
    }

    long resolve(long seed) {
        var result = seed;
        for (AlmanacMap almanacMap : almanacMaps) {
            result = almanacMap.resolve(result);
        }
        return result;
    }

    List<Range> resolveRange(Range range) {
        List<Range> current = new ArrayList<>();
        current.add(range);
        for (AlmanacMap almanacMap : almanacMaps) {
            current = current.stream()
                             .map(almanacMap::resolveRange)
                             .flatMap(List::stream)
                             .toList();
        }
        return current;
    }
}
