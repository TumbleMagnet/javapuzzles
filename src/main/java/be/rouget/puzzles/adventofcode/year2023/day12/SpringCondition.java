package be.rouget.puzzles.adventofcode.year2023.day12;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;

import java.util.Arrays;
import java.util.List;

public enum SpringCondition {
    OPERATIONAL("."),
    DAMAGED("#"),
    UNKNOWN("?");

    private final String symbol;

    SpringCondition(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<SpringCondition> getPossibleConditions() {
        return switch (this) {
            case DAMAGED -> List.of(DAMAGED);
            case OPERATIONAL -> List.of(OPERATIONAL);
            case UNKNOWN -> List.of(OPERATIONAL, DAMAGED);
        };
    }

    public static SpringCondition fromSymbol(String symbol) {
        return Arrays.stream(SpringCondition.values())
                .filter(sc -> sc.getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find any spring condition matching symbol " + symbol));
    }

    public static List<SpringCondition> fromSpringConditions(String springs) {
        return AocStringUtils.extractCharacterList(springs).stream()
                .map(SpringCondition::fromSymbol)
                .toList();
    }
}
