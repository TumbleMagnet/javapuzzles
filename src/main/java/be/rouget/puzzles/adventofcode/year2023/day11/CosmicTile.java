package be.rouget.puzzles.adventofcode.year2023.day11;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum CosmicTile implements MapCharacter {
    
    EMPTY("."),
    GALAXY("#");

    private final String mapChar;

    CosmicTile(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static CosmicTile parse(String input) {
        return Arrays.stream(CosmicTile.values())
                .filter(title -> title.getMapChar().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching tile found for "+ input));
    }
}
