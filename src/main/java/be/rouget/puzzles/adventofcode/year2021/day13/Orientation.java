package be.rouget.puzzles.adventofcode.year2021.day13;

public enum Orientation {
    HORIZONTAL,
    VERTICAL;

    public static Orientation parseInput(String input) {
        switch (input) {
            case "x": return VERTICAL;
            case "y": return HORIZONTAL;
            default:
                throw new IllegalArgumentException("Invalid input: " + input);
        }
    }
}
