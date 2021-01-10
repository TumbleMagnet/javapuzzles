package be.rouget.puzzles.adventofcode.year2020.day20;


import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;

public enum ImagePixel implements MapCharacter {
    BLACK("#"),
    WHITE(".");

    private String mapChar;

    ImagePixel(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static ImagePixel fromMapChar(String mapChar) {
        return Arrays.stream(ImagePixel.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active status for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }
}
