package be.rouget.puzzles.adventofcode.year2021.day15;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;
import lombok.Value;

@Value
public class RiskLevel implements MapCharacter {
    int value;

    @Override
    public String getMapChar() {
        return String.valueOf(value);
    }

    public static RiskLevel fromMapChar(String mapChar) {
        return new RiskLevel(Integer.valueOf(mapChar));
    }
}
