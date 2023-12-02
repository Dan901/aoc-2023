package day1;

import java.util.Map;
import java.util.Optional;

class LineCalibrator {

    private static final Map<String, Integer> VALID_SPELLED_DIGITS = Map.of("one", 1,
                                                                            "two", 2,
                                                                            "three", 3,
                                                                            "four", 4,
                                                                            "five", 5,
                                                                            "six", 6,
                                                                            "seven", 7,
                                                                            "eight", 8,
                                                                            "nine", 9);

    private final String line;
    private Integer firstDigit;
    private Integer lastDigit;

    LineCalibrator(String line) {
        this.line = line;
        combineFirstAndLastDigit();
    }

    int getCalibratedValue() {
        if (firstDigit == null || lastDigit == null) {
            throw new IllegalArgumentException("Line does not contain enough digits");
        }
        return Integer.parseInt("%d%d".formatted(firstDigit, lastDigit));
    }

    private void combineFirstAndLastDigit() {
        for (int i = 0; i < line.length(); i++) {
            var digit = findDigitAt(i);
            if (digit.isEmpty()) {
                continue;
            }

            lastDigit = digit.get();
            if (firstDigit == null) {
                firstDigit = lastDigit;
            }
        }
    }

    private Optional<Integer> findDigitAt(int index) {
        if (Character.isDigit(line.charAt(index))) {
            return Optional.of(Integer.parseInt(String.valueOf(line.charAt(index))));
        }
        return findSpelledDigitAt(index);
    }

    private Optional<Integer> findSpelledDigitAt(int index) {
        var lineFromIndex = line.substring(index);
        for (var spelledDigit : VALID_SPELLED_DIGITS.keySet()) {
            if (lineFromIndex.startsWith(spelledDigit)) {
                return Optional.of(VALID_SPELLED_DIGITS.get(spelledDigit));
            }
        }
        return Optional.empty();
    }
}
