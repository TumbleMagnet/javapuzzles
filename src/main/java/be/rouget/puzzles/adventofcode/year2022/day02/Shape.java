package be.rouget.puzzles.adventofcode.year2022.day02;

public enum Shape {
    ROCK(1L),
    PAPER(2L),
    SCISSORS(3L);

    private final long score;

    Shape(long score) {
        this.score = score;
    }

    public static Shape fromPlay1(String play1) {
        return switch (play1) {
            case "A" -> ROCK;
            case "B" -> PAPER;
            case "C" -> SCISSORS;
            default -> throw new IllegalArgumentException("Unsupported play 1: " + play1);
        };
    }

    public static Shape fromPlay2(String play2) {
        return switch (play2) {
            case "X" -> ROCK;
            case "Y" -> PAPER;
            case "Z" -> SCISSORS;
            default -> throw new IllegalArgumentException("Unsupported play 2: " + play2);
        };
    }

    public long getScore() {
        return score;
    }
}
