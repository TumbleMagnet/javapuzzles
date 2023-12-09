package be.rouget.puzzles.adventofcode.year2023.day03;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;
import org.apache.commons.lang3.StringUtils;

public record EngineCharacter(String mapChar) implements MapCharacter {

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public boolean isDigit() {
        return StringUtils.isNumeric(mapChar);
    }

    public boolean isSymbol() {
        return !isDigit() && !".".equals(mapChar);
    }
}
