package day3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

class Engine {

    private final char[][] schema;
    private final Map<PartNumber, Symbol> partNumberToSymbol = new HashMap<>();

    public Engine(char[][] schema) {
        this.schema = schema;
        findPartNumbers().forEach(partNumber -> partNumberToSymbol.put(partNumber, findAdjacentSymbol(partNumber).orElse(null)));
    }

    int sumOfPartNumbers() {
        return partNumberToSymbol.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue() != null)
                                 .mapToInt(entry -> entry.getKey().number())
                                 .sum();
    }

    int sumOfGearRatios() {
        return computeGearSymbolToPartNumbers().values()
                                               .stream()
                                               .filter(partNumbers -> partNumbers.size() == 2)
                                               .mapToInt(partNumbers -> partNumbers.get(0).number() * partNumbers.get(1).number())
                                               .sum();
    }

    private Map<Symbol, List<PartNumber>> computeGearSymbolToPartNumbers() {
        var gearSymbolToPartNumbers = new HashMap<Symbol, List<PartNumber>>();
        for (var partNumber : partNumberToSymbol.keySet()) {
            var symbol = partNumberToSymbol.get(partNumber);
            if (!isGearSymbol(symbol)) {
                continue;
            }

            gearSymbolToPartNumbers.computeIfAbsent(symbol, ignored -> new ArrayList<>())
                                   .add(partNumber);
        }
        return gearSymbolToPartNumbers;
    }

    private boolean isGearSymbol(Symbol symbol) {
        if (symbol == null) {
            return false;
        }
        return schema[symbol.row()][symbol.column()] == '*';
    }

    private List<PartNumber> findPartNumbers() {
        var partNumbers = new ArrayList<PartNumber>();
        for (int i = 0; i < schema.length; i++) {
            for (int j = 0; j < schema[i].length; j++) {
                if (!Character.isDigit(schema[i][j])) {
                    continue;
                }
                var partNumber = findPartNumber(i, j);
                partNumbers.add(partNumber);
                j = partNumber.endIndex() - 1;
            }
        }
        return partNumbers;
    }

    private PartNumber findPartNumber(int row, int column) {
        var number = new StringBuilder();
        var index = column;
        do {
            number.append(schema[row][index]);
            index++;
        } while (index < schema[row].length && Character.isDigit(schema[row][index]));
        return new PartNumber(Integer.parseInt(number.toString()), row, column, index);
    }

    private Optional<Symbol> findAdjacentSymbol(PartNumber partNumber) {
        var row = partNumber.row();
        var rowSearchStart = Math.max(row - 1, 0);
        var rowSearchEnd = Math.min(row + 2, schema.length);
        var columnSearchStart = Math.max(partNumber.startIndex() - 1, 0);
        var columnSearchEnd = Math.min(partNumber.endIndex() + 1, schema[row].length - 1);
        return IntStream.range(rowSearchStart, rowSearchEnd)
                        .mapToObj(r -> findSymbol(r, columnSearchStart, columnSearchEnd))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst();
    }

    private Optional<Symbol> findSymbol(int row, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            var symbol = findSymbol(row, i);
            if (symbol.isPresent()) {
                return symbol;
            }
        }
        return Optional.empty();
    }

    private Optional<Symbol> findSymbol(int row, int i) {
        if (isSymbol(schema[row][i])) {
            return Optional.of(new Symbol(row, i));
        }
        return Optional.empty();
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private record PartNumber(int number, int row, int startIndex, int endIndex) {

    }

    private record Symbol(int row, int column) {

    }
}
