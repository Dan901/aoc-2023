package day7;

import java.util.ArrayList;
import java.util.List;

public class CamelCards {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(7);
        var hands = inputLines.stream()
                              .map(CamelCards::parseHand)
                              .toList();
        var sortedHands = sortHands(hands);
        var totalWinnings = calculateTotalWinnings(sortedHands);
        System.out.println("Total winnings: " + totalWinnings);
    }

    private static Hand parseHand(String line) {
        var parts = line.split(" ");
        var cards = parts[0].chars().map(CamelCards::calculateCardRank).boxed().toList();
        var bid = Integer.parseInt(parts[1]);
        return new Hand(cards, bid);
    }

    private static int calculateCardRank(int symbol) {
        return switch ((char) symbol) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> symbol - '0';
        };
    }

    private static List<Hand> sortHands(List<Hand> hands) {
        var sortedHands = new ArrayList<>(hands);
        sortedHands.sort(Hand.rankComparator());
        return sortedHands;
    }

    private static int calculateTotalWinnings(List<Hand> sortedHands) {
        var totalWinnings = 0;
        for (int i = 0; i < sortedHands.size(); i++) {
            totalWinnings += sortedHands.get(i).getBid() * (i + 1);
        }
        return totalWinnings;
    }
}
