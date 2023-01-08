package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public record MapHeight(String mapChar) implements MapCharacter {

    public static final int MIN_ELEVATION = 1;

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public int getElevation() {
        if (isStart()) {
            return getElevation("a");
        } else if (isEnd()) {
            return getElevation("z");
        } else {
            return getElevation(mapChar);
        }
    }

    public static int getElevation(String input) {
        if (input.length() != 1) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        char mapChar = input.charAt(0);
        return MIN_ELEVATION + mapChar - 'a';
    }

    public boolean isEnd() {
        return "E".equals(mapChar);
    }

    public boolean isStart() {
        return "S".equals(mapChar);
    }
}
