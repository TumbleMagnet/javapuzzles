package be.rouget.puzzles.adventofcode.year2024.day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record Report(List<Integer> levels) {

    public Report(List<Integer> levels) {
        this.levels = List.copyOf(levels);
    }

    public boolean isSafe() {
        // Safe when elements are either in ascending or descending order
        // and increment is between 1 and 3 (included)
        Boolean ascending = null;
        for (int i = 0; i < levels().size() - 1; i++) {
            int first = levels().get(i);
            int second = levels().get(i+1);

            if (ascending == null) {
                // For first pair, record direction
                ascending = second > first;
            } else {
                // Check that current pair is in the right direction
                if (second > first != ascending) {
                    return false;
                }
            }

            // Check difference
            int difference = Math.abs(second - first);
            if (difference < 1 || difference > 3) {
                return false;
            }
        }
        return true;
    }

    public boolean isSafeWithDampener() {
        if (isSafe()) {
            // Safe without modification
            return true;
        }

        // Check whether there exists a safe report with one element less
        return IntStream.range(0, levels.size())
                .anyMatch(i -> excludeElementAtIndex(i).isSafe());
    }

    public Report excludeElementAtIndex(int indexToExclude) {
        List<Integer> filteredLevels = IntStream.range(0, levels.size())
                .filter(index -> index != indexToExclude)
                .mapToObj(levels::get)
                .toList();
        return new Report(filteredLevels);
    }

    public static Report parse(String input) {
        String[] tokens = input.split(" ");
        List<Integer> levels = Arrays.stream(tokens)
                .map(Integer::parseInt)
                .toList();
        return new Report(levels);
    }
}
