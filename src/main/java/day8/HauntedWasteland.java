package day8;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HauntedWasteland {

    public static void main(String[] args) {
        var inputLines = common.InputReader.readInput(8);
        var directions = inputLines.get(0).toCharArray();
        var map = parseMap(inputLines.subList(2, inputLines.size()));
        var steps = navigate(directions, map);
        System.out.println("Result is least common multiple of: " + steps);
    }

    private static List<Integer> navigate(char[] directions, Map<String, Node> map) {
        var current = getStartingNodes(map);
        return current.stream()
                      .map(node -> stepsUntilEndingNode(node, map, directions))
                      .toList();
    }

    private static int stepsUntilEndingNode(Node node, Map<String, Node> map, char[] directions) {
        var steps = 0;
        while (!node.name().endsWith("Z")) {
            node = map.get(directions[steps % directions.length] == 'L' ? node.left() : node.right());
            steps++;
        }
        return steps;
    }

    private static List<Node> getStartingNodes(Map<String, Node> map) {
        return map.keySet()
                  .stream()
                  .filter(node -> node.endsWith("A"))
                  .map(map::get)
                  .toList();
    }

    private static Map<String, Node> parseMap(List<String> lines) {
        return lines.stream()
                    .map(HauntedWasteland::parseNode)
                    .collect(toMap(Node::name, Function.identity()));
    }

    private static Node parseNode(String line) {
        var parts = line.split(" = ");
        var name = parts[0];
        var nextNodes = parts[1].substring(1, parts[1].length() - 1).split(", ");
        return new Node(name, nextNodes[0], nextNodes[1]);
    }
}
