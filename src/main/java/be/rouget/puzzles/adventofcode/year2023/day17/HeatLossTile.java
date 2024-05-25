package be.rouget.puzzles.adventofcode.year2023.day17;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public record HeatLossTile(int heatLoss) implements MapCharacter {

    @Override
    public String getMapChar() {
        return String.valueOf(heatLoss);
    }

    public static HeatLossTile parse(String input) {
        int value = Integer.parseInt(input);
        return new HeatLossTile(value);
    }
}
