package be.rouget.puzzles.adventofcode.year2024.day15;

import java.util.Arrays;

public enum Command {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">");

    private final String commandCharacter;

    Command(String commandCharacter) {
        this.commandCharacter = commandCharacter;
    }

    public String getCommandCharacter() {
        return commandCharacter;
    }

    public static Command parse(String inputChar) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommandCharacter().equals(inputChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid command: " + inputChar));
    }
}
