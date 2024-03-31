package be.rouget.puzzles.adventofcode.year2023.day14;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum PlatformTile implements MapCharacter {
    
    EMPTY("."),
    CUBE("#"),
    ROUND_ROCK("O");

    private final String mapChar;

    PlatformTile(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public static PlatformTile parse(String input) {
        return Arrays.stream(PlatformTile.values())
                .filter(title -> title.getMapChar().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching tile found for "+ input));
    }
}
