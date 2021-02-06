package be.rouget.puzzles.adventofcode.year2015.day6;

public enum Instruction {
    TURN_ON,
    TURN_OFF,
    TOGGLE;

    public static Instruction fromInput(String input) {
        switch (input) {
            case "turn on": return TURN_ON;
            case "turn off": return TURN_OFF;
            case "toggle": return TOGGLE;
            default:
                throw new IllegalArgumentException("Invalid command input: " + input);
        }
    }
}
