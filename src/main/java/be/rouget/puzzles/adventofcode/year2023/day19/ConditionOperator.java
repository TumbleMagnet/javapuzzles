package be.rouget.puzzles.adventofcode.year2023.day19;

import java.util.Arrays;

public enum ConditionOperator {
    GREATER_THAN(">"),
    SMALLER_THAN("<");
    
    private final String input;

    ConditionOperator(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public static ConditionOperator fromInput(String input) {
        return Arrays.stream(ConditionOperator.values())
                .filter(conditionOperator -> conditionOperator.getInput().equals(input))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + input));
    }
}
