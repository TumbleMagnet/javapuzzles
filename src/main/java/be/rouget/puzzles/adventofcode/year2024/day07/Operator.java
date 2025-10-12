package be.rouget.puzzles.adventofcode.year2024.day07;

public enum Operator {
    ADD,
    MULTIPLY;

    public long evaluate(long leftValue, long rightValue) {
        return switch (this) {
            case ADD -> leftValue + rightValue;
            case MULTIPLY -> leftValue * rightValue;
        };
    }
}
