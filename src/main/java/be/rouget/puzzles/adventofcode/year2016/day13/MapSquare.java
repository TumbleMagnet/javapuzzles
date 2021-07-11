package be.rouget.puzzles.adventofcode.year2016.day13;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum MapSquare implements MapCharacter {
    WALL("#"),
    OPEN(".");

    private final String mapChar;

    MapSquare(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static MapSquare fromMapChar(String mapChar) {
        return Arrays.stream(MapSquare.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MapSquare value for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }

}
