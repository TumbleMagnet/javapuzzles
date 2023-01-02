package be.rouget.puzzles.adventofcode.year2022.day10;

public enum InstructionCode {
    NOOP(1),
    ADDX(2);

    InstructionCode(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    private final int numberOfCycles;

    public int getNumberOfCycles() {
        return numberOfCycles;
    }
}
