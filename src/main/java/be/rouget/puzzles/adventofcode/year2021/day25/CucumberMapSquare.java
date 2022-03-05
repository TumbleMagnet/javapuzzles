package be.rouget.puzzles.adventofcode.year2021.day25;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum CucumberMapSquare implements MapCharacter {
    EAST(">"),
    SOUTH("v"),
    EMPTY(".");

    private final String mapChar;

    CucumberMapSquare(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static CucumberMapSquare fromMapChar(String mapChar) {
        return Arrays.stream(CucumberMapSquare.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MapSquare value for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }

}
