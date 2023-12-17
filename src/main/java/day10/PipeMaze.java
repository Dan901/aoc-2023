package day10;

import java.util.List;

public class PipeMaze {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(10);
        var maze = parseMaze(inputLines);
        var steps = maze.stepsToFarthestPoint();
        System.out.println("Steps to farthest point: " + steps);
        var enclosedTiles = maze.findEnclosedTiles();
        System.out.println("Enclosed tiles: " + enclosedTiles);
    }

    private static Maze parseMaze(List<String> lines) {
        var maze = lines.stream()
                        .map(PipeMaze::parseLine)
                        .toArray(Tile[][]::new);
        return new Maze(maze);
    }

    private static Tile[] parseLine(String line) {
        return line.chars()
                   .mapToObj(c -> (char) c)
                   .map(Tile::fromSymbol)
                   .toArray(Tile[]::new);
    }
}
