package be.rouget.puzzles.adventofcode.year2023.day13;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum GroundTile implements MapCharacter {
    
    ASH("."),
    ROCK("#");

    private final String mapChar;

    GroundTile(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static GroundTile parse(String input) {
        return Arrays.stream(GroundTile.values())
                .filter(title -> title.getMapChar().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching tile found for "+ input));
    }
}
