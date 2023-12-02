package common;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputReader {

    public static List<String> readInput(int day, int part) {
        var inputPath = Paths.get("src/main/resources/day%d/input-%d.txt".formatted(day, part));
        try {
            return Files.readAllLines(inputPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
