package day6;

import java.util.Arrays;
import java.util.List;

public class BoatRacing {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(6);
        var race = parseRaces(inputLines);
        System.out.println("Ways to win race " + race.waysToWin());
    }

    private static Race parseRaces(List<String> inputLines) {
        var time = parseNumber(inputLines.get(0));
        var distance = parseNumber(inputLines.get(1));
        return new Race(time, distance);
    }

    private static long parseNumber(String line) {
        var numbersString = line.split(":")[1].trim();
        var numbers = Arrays.stream(numbersString.split(" +")).toList();
        return Long.parseLong(String.join("", numbers));
    }
}
