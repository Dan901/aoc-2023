package day4;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class Card {

    private final Set<Integer> winningNumbers;
    private final List<Integer> numbers;

    long countMatchingNumbers() {
        return numbers.stream()
                      .filter(winningNumbers::contains)
                      .count();
    }

    long calculatePoints() {
        var matches = countMatchingNumbers();
        if (matches == 0) {
            return 0;
        }
        var score = 1L;
        for (var i = 1; i < matches; i++) {
            score *= 2;
        }
        return score;
    }
}
