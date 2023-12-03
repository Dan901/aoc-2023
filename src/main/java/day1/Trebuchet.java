package day1;

import common.InputReader;
import java.util.List;

public class Trebuchet {

    public static void main(String[] args) {
        var inputLines = InputReader.readInput(1);
        var calibrationValue = calculateCalibrationValue(inputLines);
        System.out.printf("Calibration value: %d%n", calibrationValue);
    }

    private static int calculateCalibrationValue(List<String> inputLines) {
        return inputLines.stream()
                         .map(LineCalibrator::new)
                         .mapToInt(LineCalibrator::getCalibratedValue)
                         .sum();
    }
}
