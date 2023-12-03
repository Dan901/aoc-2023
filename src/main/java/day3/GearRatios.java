package day3;

public class GearRatios {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(3);
        var engineSchema = inputLines.stream()
                              .map(String::toCharArray)
                              .toArray(char[][]::new);
        var engine = new Engine(engineSchema);
        System.out.println("Sum of part numbers: " + engine.sumOfPartNumbers());
        System.out.println("Sum of gear ratios: " + engine.sumOfGearRatios());
    }
}
