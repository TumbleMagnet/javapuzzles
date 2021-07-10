package be.rouget.puzzles.adventofcode.year2016.day12;

public enum InstructionCode {
    CPY, INC, DEC, JNZ;

    public static InstructionCode parse(String input) {
        return InstructionCode.valueOf(input.toUpperCase());
    }
}
