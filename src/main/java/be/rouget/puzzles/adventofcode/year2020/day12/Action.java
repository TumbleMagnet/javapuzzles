package be.rouget.puzzles.adventofcode.year2020.day12;

import java.util.Arrays;

public enum Action {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W"),
    LEFT("L"),
    RIGHT("R"),
    FORWARD("F");

    private String code;

    Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Action fromCode(String code) {
        return Arrays.stream(Action.values())
            .filter(action -> action.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No action found for code: " + code));
    }
}
