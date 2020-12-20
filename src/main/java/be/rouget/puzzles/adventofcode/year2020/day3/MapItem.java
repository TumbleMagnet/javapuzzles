package be.rouget.puzzles.adventofcode.year2020.day3;

import java.util.Arrays;

public enum MapItem {
    EMPTY("."),
    TREE("#");

    private String mapChar;

    MapItem(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static MapItem fromMapCharacter(String mapChar) {
        return Arrays.stream(MapItem.values())
                .filter(i -> i.getMapChar().equals(mapChar))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No map item for character: " + mapChar));
    }
}
