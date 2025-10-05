package be.rouget.puzzles.adventofcode.year2024.day06;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum LabChar implements MapCharacter {
    FREE_SPACE("."),
    OBSTACLE("#"),
    GUARD("^");

    private final String mapChar;

    LabChar(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static LabChar parse(String input) {
        return Arrays.stream(LabChar.values())
                .filter(labChar -> labChar.mapChar.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot parse lab character: " + input));
    }
}
