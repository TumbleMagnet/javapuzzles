package be.rouget.puzzles.adventofcode.year2015.day18;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum LightState implements MapCharacter {
    ON("#"),
    OFF(".");

    private final String mapChar;

    LightState(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static LightState fromMapChar(String mapChar) {
        return Arrays.stream(LightState.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No light status for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }
}
