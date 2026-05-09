package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum WarehouseItem implements MapCharacter {
    EMPTY("."),
    WALL("#"),
    BOX("O"),
    ROBOT("@")
    ;

    private final String mapChar;

    WarehouseItem(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return this.mapChar;
    }

    public static WarehouseItem parse(String input) {
        return Arrays.stream(WarehouseItem.values())
                .filter(item -> item.getMapChar().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid map character: " + input));
    }
}
