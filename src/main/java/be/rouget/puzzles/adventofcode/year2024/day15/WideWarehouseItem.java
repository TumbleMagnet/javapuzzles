package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum WideWarehouseItem implements MapCharacter {
    EMPTY("."),
    WALL("#"),
    BOX_LEFT("["),
    BOX_RIGHT("]"),
    ROBOT("@")
    ;

    private final String mapChar;

    WideWarehouseItem(String mapChar) {
        this.mapChar = mapChar;
    }

    @Override
    public String getMapChar() {
        return this.mapChar;
    }

    public static WideWarehouseItem parse(String input) {
        return Arrays.stream(WideWarehouseItem.values())
                .filter(item -> item.getMapChar().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid map character: " + input));
    }
}
