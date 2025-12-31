package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public record Plant(String type) implements MapCharacter {

    @Override
    public String getMapChar() {
        return type;
    }
}
