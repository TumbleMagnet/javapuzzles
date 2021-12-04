package be.rouget.puzzles.adventofcode.year2021.day2;

public enum Command {
    FORWARD, DOWN, UP;

    public static Command fromInput(String input) {
        return Command.valueOf(input.toUpperCase());
    }
}
