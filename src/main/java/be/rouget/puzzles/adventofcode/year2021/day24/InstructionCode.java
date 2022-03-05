package be.rouget.puzzles.adventofcode.year2021.day24;

import java.util.Arrays;

public enum InstructionCode {
    INPUT("inp"),
    ADD("add"),
    MULTIPLY("mul"),
    DIVIDE("div"),
    MODULO("mod"),
    EQUALS("eql"),
    ;

    InstructionCode(String keyword) {
        this.keyword = keyword;
    }

    private final String keyword;

    public static InstructionCode fromKeyword(String keyword) {
        return Arrays.stream(InstructionCode.values())
                .filter(instructionCode -> instructionCode.keyword.equals(keyword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid keyword: " + keyword));
    }
}
