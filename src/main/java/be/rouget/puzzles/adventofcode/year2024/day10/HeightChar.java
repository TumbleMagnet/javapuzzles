package be.rouget.puzzles.adventofcode.year2024.day10;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public record HeightChar(int height) implements MapCharacter {

    public static HeightChar parse(String inputCharacter) {
        return new HeightChar(Integer.parseInt(inputCharacter));
    }

    @Override
    public String getMapChar() {
        return String.valueOf(height);
    }
}
