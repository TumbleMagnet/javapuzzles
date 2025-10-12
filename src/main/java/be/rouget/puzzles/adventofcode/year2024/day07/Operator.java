package be.rouget.puzzles.adventofcode.year2024.day07;

public enum Operator {
    ADD,
    MULTIPLY,
    CONCATENATE;

    public long evaluate(long leftValue, long rightValue) {
        return switch (this) {
            case ADD -> leftValue + rightValue;
            case MULTIPLY -> leftValue * rightValue;
            case CONCATENATE -> Long.parseLong(leftValue + String.valueOf(rightValue));
        };
    }
}
