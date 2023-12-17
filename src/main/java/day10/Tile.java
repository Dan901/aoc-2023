package day10;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum Tile {
    VERTICAL_PIPE('|', Set.of(Direction.NORTH, Direction.SOUTH)),
    HORIZONTAL_PIPE('-', Set.of(Direction.WEST, Direction.EAST)),
    NE_BEND('L', Set.of(Direction.NORTH, Direction.EAST)),
    NW_BEND('J', Set.of(Direction.NORTH, Direction.WEST)),
    SW_BEND('7', Set.of(Direction.SOUTH, Direction.WEST)),
    SE_BEND('F', Set.of(Direction.SOUTH, Direction.EAST)),
    GROUND('.', Set.of()),
    START('S', Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));

    private final char symbol;
    @Getter
    private final Set<Direction> connects;

    static Tile fromSymbol(char symbol) {
        for (var tile : Tile.values()) {
            if (tile.symbol == symbol) {
                return tile;
            }
        }
        throw new IllegalArgumentException("No tile with symbol " + symbol);
    }
}
