package day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.ToString;

@ToString
class Maze {

    private final Tile[][] tiles;
    private final boolean[][] loop;
    private final int[][] enclosed;

    public Maze(Tile[][] tiles) {
        this.tiles = tiles;
        this.loop = new boolean[tiles.length][tiles[0].length];
        this.enclosed = new int[tiles.length][tiles[0].length];
    }

    int stepsToFarthestPoint() {
        var start = findStart();
        var currentSteps = 0;
        markLoop(start);
        var nextPositions = findInitialLoopPositions(start);
        deduceStartTile(nextPositions, start);

        while (true) {
            currentSteps++;
            for (var nextPosition : nextPositions) {
                markLoop(nextPosition.position());
            }
            if (nextPositions.stream().map(NextPosition::position).collect(Collectors.toSet()).size() == 1) {
                break;
            }
            nextPositions = nextPositions
                    .stream()
                    .map(this::getNextLoopPosition)
                    .toList();
        }
        return currentSteps;
    }

    int findEnclosedTiles() {
        for (int y = 0; y < loop.length; y++) {
            findEnclosedTilesInRow(y);
        }
        return countEnclosedTiles();
    }

    private void findEnclosedTilesInRow(int y) {
        var inside = false;
        Direction loopStartDirection = null;
        for (int x = 0; x < loop[y].length; x++) {
            if (!loop[y][x]) {
                if (inside) {
                    enclosed[y][x] = 1;
                }
                continue;
            }
            var tile = getTile(new Position(x, y));
            if (loopStartDirection == null) {
                if (tile == Tile.NE_BEND) {
                    loopStartDirection = Direction.NORTH;
                    continue;
                }
                if (tile == Tile.SE_BEND) {
                    loopStartDirection = Direction.SOUTH;
                    continue;
                }
            }
            if (loopStartDirection == Direction.NORTH && tile == Tile.NW_BEND
                    || loopStartDirection == Direction.SOUTH && tile == Tile.SW_BEND) {
                loopStartDirection = null;
                continue;
            }
            if (tile != Tile.HORIZONTAL_PIPE) {
                loopStartDirection = null;
                inside = !inside;
            }
        }
    }

    private int countEnclosedTiles() {
        var count = 0;
        for (int i = 0; i < enclosed.length; i++) {
            for (int j = 0; j < enclosed[i].length; j++) {
                if (enclosed[i][j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    private void deduceStartTile(List<NextPosition> nextPositions, Position start) {
        var directions = nextPositions.stream().map(NextPosition::direction).collect(Collectors.toSet());
        var startTile = Arrays.stream(Tile.values()).filter(tile -> tile.getConnects().equals(directions)).findFirst()
                              .orElseThrow(() -> new IllegalStateException("Invalid start tile"));
        tiles[start.y][start.x] = startTile;
    }

    private NextPosition getNextLoopPosition(NextPosition nextPosition) {
        var tile = getTile(nextPosition.position());
        var direction = tile.getConnects().stream().filter(dir -> dir != nextPosition.direction().opposite()).findFirst()
                            .orElseThrow(() -> new IllegalStateException("No direction found"));
        return requireNextPosition(nextPosition.position(), direction);
    }

    private NextPosition requireNextPosition(Position current, Direction direction) {
        return getNextPosition(current, direction).orElseThrow(() -> new IllegalStateException("Invalid next position"));
    }

    private void markLoop(Position position) {
        loop[position.y][position.x] = true;
    }

    private List<NextPosition> findInitialLoopPositions(Position start) {
        List<NextPosition> nextPositions = new ArrayList<>();
        for (var direction : Direction.values()) {
            getNextPosition(start, direction)
                    .filter(nextPosition -> getTile(nextPosition.position()).getConnects().contains(direction.opposite()))
                    .ifPresent(nextPositions::add);
        }
        return nextPositions;
    }

    private Tile getTile(Position position) {
        return tiles[position.y][position.x];
    }

    private Optional<NextPosition> getNextPosition(Position current, Direction direction) {
        var next = new Position(current.x + direction.getX(), current.y + direction.getY());
        return isValid(next) ? Optional.of(new NextPosition(next, direction)) : Optional.empty();
    }

    private boolean isValid(Position position) {
        return position.x >= 0 && position.x < tiles[0].length && position.y >= 0 && position.y < tiles.length;
    }

    private Position findStart() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == Tile.START) {
                    return new Position(x, y);
                }
            }
        }
        throw new IllegalStateException("Start not found");
    }

    private record NextPosition(Position position, Direction direction) {

    }

    private record Position(int x, int y) {

    }
}
