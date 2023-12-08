package day7;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class Hand {

    private static final int HAND_SIZE = 5;
    private static final int JOKER = 1;

    private final List<Integer> cards;
    private final int bid;
    private final HandType handType;

    Hand(List<Integer> cards, int bid) {
        if (cards.size() != HAND_SIZE) {
            throw new IllegalArgumentException("Hand must contain exactly 5 cards");
        }
        this.cards = cards;
        this.bid = bid;
        this.handType = calculateHandType();
    }

    private HandType calculateHandType() {
        var cardCounts = cards.stream()
                              .collect(toMap(Function.identity(), c -> 1, Integer::sum));
        var sortedCardCounts = resolveJokers(cardCounts);
        return calculateHandType(sortedCardCounts);
    }

    private List<Integer> resolveJokers(Map<Integer, Integer> cardCounts) {
        var jokers = cardCounts.getOrDefault(JOKER, 0);
        if (jokers == HAND_SIZE) {
            return List.of(HAND_SIZE);
        }
        cardCounts.remove(JOKER);
        var sortedCardCounts = new ArrayList<>(cardCounts.values());
        sortedCardCounts.sort(Comparator.reverseOrder());
        for (int i = 0; i < sortedCardCounts.size(); i++) {
            if (jokers == 0) {
                break;
            }
            var addedJokers = Math.min(jokers, HAND_SIZE - sortedCardCounts.get(i));
            sortedCardCounts.set(i, sortedCardCounts.get(i) + addedJokers);
            jokers -= addedJokers;
        }
        return sortedCardCounts;
    }

    private HandType calculateHandType(List<Integer> sortedCardCounts) {
        return switch (sortedCardCounts.getFirst()) {
            case 5 -> HandType.FIVE_OF_A_KIND;
            case 4 -> HandType.FOUR_OF_A_KIND;
            case 3 -> sortedCardCounts.size() == 2 ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
            case 2 -> sortedCardCounts.size() == 3 ? HandType.TWO_PAIRS : HandType.ONE_PAIR;
            default -> HandType.HIGH_CARD;
        };
    }

    static Comparator<Hand> rankComparator() {
        var comparator = Comparator.<Hand>comparingInt(hand -> hand.getHandType().getRank());
        for (int i = 0; i < HAND_SIZE; i++) {
            int finalI = i;
            comparator = comparator.thenComparing(hand -> hand.getCards().get(finalI));
        }
        return comparator;
    }
}
