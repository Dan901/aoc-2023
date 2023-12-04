package day4;

import java.util.Arrays;
import java.util.List;

class Cards {

    private final int[] cardCounts;

    Cards(List<Card> cards) {
        this.cardCounts = new int[cards.size()];
        Arrays.fill(cardCounts, 1);
        calculateCardCounts(cards);
    }

    int totalCardCount() {
        return Arrays.stream(cardCounts).sum();
    }

    private void calculateCardCounts(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            var matches = cards.get(i).countMatchingNumbers();
            if (matches == 0) {
                continue;
            }

            for (int j = 1; j <= matches; j++) {
                if (i + j >= cards.size()) {
                    break;
                }
                cardCounts[i + j] += cardCounts[i];
            }
        }
    }
}
