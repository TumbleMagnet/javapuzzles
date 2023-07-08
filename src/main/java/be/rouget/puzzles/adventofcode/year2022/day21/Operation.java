package be.rouget.puzzles.adventofcode.year2022.day21;

import java.util.Arrays;

public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");
    
    private final String token;

    Operation(String token) {
        this.token = token;
    }

    public static Operation fromToken(String token) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.token.equals(token))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operation: " + token));
    }
}
