package be.rouget.puzzles.adventofcode.year2022.day02;

import java.util.Arrays;

public enum Outcome {
    WIN("Z", 6L),
    DRAW("Y", 3L),
    LOSS("X", 0L);

    private final String code;
    private final long score;

    Outcome(String code, long score) {
        this.code = code;
        this.score = score;
    }

    public long getScore() {
        return score;
    }

    public static Outcome fromCode(String inputCode) {
        return Arrays.stream(Outcome.values())
                .filter(o -> o.code.equals(inputCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No outcome found for code " + inputCode));
    }
}
