package be.rouget.puzzles.adventofcode.year2024.day05;

public record PrintRule(int first, int second) {

    public static PrintRule parse(String input) {
        String[] tokens = input.split("\\|");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        int first = Integer.parseInt(tokens[0]);
        int second = Integer.parseInt(tokens[1]);
        return new PrintRule(first, second);
    }
}
