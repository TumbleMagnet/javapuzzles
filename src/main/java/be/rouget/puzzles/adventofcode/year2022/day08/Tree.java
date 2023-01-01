package be.rouget.puzzles.adventofcode.year2022.day08;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public record Tree(int size) implements MapCharacter {
    @Override
    public String getMapChar() {
        return String.valueOf(size());
    }

    public static Tree parse(String input) {
        int size = Integer.parseInt(input);
        if (size < 0 || size > 9) {
            throw new IllegalArgumentException("Invalid tree size: " + input);
        }
        return new Tree(size);
    }
}
