package day5;

record RangeMapping(long sourceStart, long destinationStart, long length) {

    boolean inRange(long number) {
        return number >= sourceStart && number < sourceStart + length;
    }

    long map(long number) {
        return destinationStart + number - sourceStart;
    }

    Range map(Range range) {
        long start = map(range.start());
        long end = Math.min(map(range.end()), destinationStart + length - 1);
        return new Range(start, end);
    }
}
