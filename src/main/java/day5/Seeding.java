package day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seeding {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(5);
        var almanac = new Almanac();
        var seeds = parseInput(inputLines, almanac);

        var closestLocation = seeds.stream()
                                   .mapToLong(almanac::resolve)
                                   .min()
                                   .getAsLong();
        System.out.println("Closest location: " + closestLocation);

        var seedRanges = createRanges(seeds);
        var closestLocationFromRanges = seedRanges.stream()
                                                  .map(almanac::resolveRange)
                                                  .flatMap(List::stream)
                                                  .mapToLong(Range::start)
                                                  .min()
                                                  .getAsLong();
        System.out.println("Closest location from ranges: " + closestLocationFromRanges);
    }

    private static List<Long> parseInput(List<String> inputLines, Almanac almanac) {
        List<Long> seeds = null;
        for (String line : inputLines) {
            if (line.isBlank()) {
                continue;
            }
            if (line.startsWith("seeds:")) {
                seeds = parseNumbers(line.split(": ")[1]);
                continue;
            }
            if (line.endsWith("map:")) {
                almanac.addMap(new AlmanacMap(line.split(" ")[0]));
                continue;
            }
            var range = parseRange(line);
            almanac.getLast().addRange(range);
        }
        return seeds;
    }

    private static RangeMapping parseRange(String line) {
        var numbers = parseNumbers(line);
        return new RangeMapping(numbers.get(1), numbers.get(0), numbers.get(2));
    }

    private static List<Long> parseNumbers(String numbers) {
        return Arrays.stream(numbers.split(" "))
                     .map(Long::parseLong)
                     .toList();
    }

    private static List<Range> createRanges(List<Long> seeds) {
        var ranges = new ArrayList<Range>();
        for (int i = 0; i < seeds.size() - 1; i += 2) {
            var start = seeds.get(i);
            var end = start + seeds.get(i + 1) - 1;
            ranges.add(new Range(start, end));
        }
        return ranges;
    }
}
