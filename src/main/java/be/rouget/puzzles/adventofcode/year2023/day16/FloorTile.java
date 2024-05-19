package be.rouget.puzzles.adventofcode.year2023.day16;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public enum FloorTile implements MapCharacter {
    
    EMPTY("."),
    MIRROR_FORWARD("/"),
    MIRROR_BACKWARD("\\"),
    SPLITTER_VERTICAL("|"),
    SPLITTER_HORIZONTAL("-");
    
    private final String mapChar;

    FloorTile(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return "";
    }
    
    public static FloorTile fromMapChar(String mapChar) {
        for (FloorTile tile : values()) {
            if (tile.mapChar.equals(mapChar)) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Unknown map char: " + mapChar);
    }
}
