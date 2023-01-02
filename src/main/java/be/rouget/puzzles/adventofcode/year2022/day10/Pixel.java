package be.rouget.puzzles.adventofcode.year2022.day10;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public enum Pixel implements MapCharacter {
    NONE("?"),
    LIT("█"),
    DARK(" ");

    Pixel(String character) {
        this.mapChar = character;
    }

    private final String mapChar;

    @Override
    public String getMapChar() {
        return mapChar;
    }
}
