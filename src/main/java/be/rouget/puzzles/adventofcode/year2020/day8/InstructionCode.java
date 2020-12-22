package be.rouget.puzzles.adventofcode.year2020.day8;

public enum InstructionCode {
    ACC,
    JMP,
    NOP;



    public static InstructionCode fromCode(String code) {
        try {
            return InstructionCode.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid code " + code, e);
        }
    }
}
