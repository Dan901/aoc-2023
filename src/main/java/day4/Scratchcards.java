package day4;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scratchcards {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(4);
        var cardList = inputLines.stream()
                              .map(line -> line.split(": +"))
                              .map(parts -> parseCard(parts[1]))
                              .toList();
        var totalPoints = cardList.stream()
                               .mapToLong(Card::calculatePoints)
                               .sum();
        System.out.println("Points: " + totalPoints);
        var cards = new Cards(cardList);
        System.out.println("Total card count: " + cards.totalCardCount());
    }

    private static Card parseCard(String line) {
        var parts = line.split(" \\| +");
        var winningNumbers = parseNumbers(parts[0]).collect(Collectors.toSet());
        var numbers = parseNumbers(parts[1]).toList();
        return new Card(winningNumbers, numbers);
    }

    private static Stream<Integer> parseNumbers(String numbers) {
        return Arrays.stream(numbers.split(" +"))
                     .map(Integer::parseInt);
    }
}
