package day2;

import common.InputReader;
import java.util.Arrays;

public class CubeConundrum {

    public static void main(String[] args) {
        var inputLines = InputReader.readInput(2, 1);
        var games = inputLines.stream()
                              .map(CubeConundrum::parseGame)
                              .toList();
        var idSumOfPossibleGames = games.stream()
                                        .filter(game -> game.isPossible(12, 13, 14))
                                        .mapToInt(Game::getId)
                                        .sum();
        System.out.println("Sum of IDs of possible games: " + idSumOfPossibleGames);

        var powerSetsSum = games.stream()
                                .mapToInt(Game::calculatePowerOfCubeSets)
                                .sum();
        System.out.println("Sum of power sets: " + powerSetsSum);
    }

    private static Game parseGame(String line) {
        var parts = line.split(": ");
        var id = Integer.parseInt(parts[0].split(" ")[1]);
        var cubeSubsets = Arrays.stream(parts[1].split("; "))
                                .map(CubeConundrum::parseRevealedCubes)
                                .toList();
        return new Game(id, cubeSubsets);
    }

    private static RevealedCubes parseRevealedCubes(String revealedCubesString) {
        var parts = revealedCubesString.split(", ");
        var revealedCubes = new RevealedCubes();
        for (String part : parts) {
            var cubeParts = part.split(" ");
            var count = Integer.parseInt(cubeParts[0]);
            var color = cubeParts[1];
            revealedCubes.setCubeCount(count, color);
        }
        return revealedCubes;
    }
}
