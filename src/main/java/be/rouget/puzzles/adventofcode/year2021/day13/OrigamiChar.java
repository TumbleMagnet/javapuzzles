package be.rouget.puzzles.adventofcode.year2021.day13;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public enum OrigamiChar implements MapCharacter {

    EMPTY(" "),
    DOT("\u2588");

    private final String mapChar;

    OrigamiChar(String character) {
        this.mapChar = character;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }
}
