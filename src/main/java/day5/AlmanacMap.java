package day5;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AlmanacMap {

    private final String name;
    private final TreeMap<Long, RangeMapping> ranges = new TreeMap<>();

    void addRange(RangeMapping rangeMapping) {
        ranges.put(rangeMapping.sourceStart(), rangeMapping);
    }

    long resolve(long number) {
        var rangeEntry = ranges.floorEntry(number);
        if (rangeEntry != null && rangeEntry.getValue().inRange(number)) {
            return rangeEntry.getValue().map(number);
        }
        return number;
    }

    List<Range> resolveRange(Range range) {
        var result = new ArrayList<Range>();
        var current = range;
        do {
            var mappedRange = mapRange(current);
            result.add(mappedRange);
            current = new Range(current.start() + mappedRange.length(), current.end());
        } while (current.length() > 0);
        return result;
    }

    private Range mapRange(Range current) {
        var rangeEntry = ranges.floorEntry(current.start());
        if (rangeEntry != null && rangeEntry.getValue().inRange(current.start())) {
            return rangeEntry.getValue().map(current);
        }

        rangeEntry = ranges.ceilingEntry(current.start());
        if (rangeEntry != null && rangeEntry.getValue().inRange(current.end())) {
            return new Range(current.start(), rangeEntry.getValue().sourceStart() - 1);
        }

        return new Range(current.start(), current.end());
    }
}
