package day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Oasis {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(9);
        var histories = inputLines.stream()
                                  .map(Oasis::parseNumbers)
                                  .toList();
        var result = histories.stream()
                              .mapToInt(Oasis::predictNextValueRecursively)
                              .sum();
        System.out.println("Result: " + result);

        var result2 = histories.stream()
                               .mapToInt(Oasis::predictPreviousValueRecursively)
                               .sum();
        System.out.println("Result2: " + result2);
    }

    private static int predictPreviousValueRecursively(List<Integer> current) {
        var differences = calculateDifferences(current);
        if (areAllZero(differences)) {
            return current.getFirst();
        }
        return current.getFirst() - predictPreviousValueRecursively(differences);
    }

    private static int predictNextValueRecursively(List<Integer> current) {
        var differences = calculateDifferences(current);
        if (areAllZero(differences)) {
            return current.getLast();
        }
        return current.getLast() + predictNextValueRecursively(differences);
    }

    private static List<Integer> calculateDifferences(List<Integer> current) {
        var differences = new ArrayList<Integer>();
        for (int i = 0; i < current.size() - 1; i++) {
            differences.add(current.get(i + 1) - current.get(i));
        }
        return differences;
    }

    private static boolean areAllZero(List<Integer> differences) {
        return differences.stream().allMatch(d -> d == 0);
    }

    private static List<Integer> parseNumbers(String line) {
        return Arrays.stream(line.split(" "))
                     .map(Integer::parseInt)
                     .collect(Collectors.toList());
    }
}
