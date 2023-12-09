package be.rouget.puzzles.adventofcode.year2016.day18;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum Tile implements MapCharacter {
    SAFE("."),
    TRAP("^");

    private final String code;

    Tile(String code) {
        this.code = code;
    }

    @Override
    public String getMapChar() {
        return code;
    }

    public static Tile fromMapChar(String mapChar) {
        return Arrays.stream(Tile.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Tile value for char " + mapChar));
    }
}
