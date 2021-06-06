package be.rouget.puzzles.adventofcode.year2016.day8;


import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum ScreenPixel implements MapCharacter {
    ON("#"),
    OFF(".");

    private String mapChar;

    ScreenPixel(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static ScreenPixel fromMapChar(String mapChar) {
        return Arrays.stream(ScreenPixel.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active status for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }
}
