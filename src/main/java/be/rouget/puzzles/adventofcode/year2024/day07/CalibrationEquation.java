package be.rouget.puzzles.adventofcode.year2024.day07;

import java.util.Arrays;
import java.util.List;

public record CalibrationEquation(long result, List<Long> values) {

    public static CalibrationEquation parse(String input) {
        // 10274: 2 923 7 658 40 4 70
        String[] parts = input.split(": ");
        long result = Long.parseLong(parts[0]);
        String[] valueTokens = parts[1].split(" ");
        List<Long> values = Arrays.stream(valueTokens).map(Long::parseLong).toList();
        return new CalibrationEquation(result, values);
    }
}
