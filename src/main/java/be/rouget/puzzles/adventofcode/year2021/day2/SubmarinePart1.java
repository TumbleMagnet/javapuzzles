package be.rouget.puzzles.adventofcode.year2021.day2;

public class SubmarinePart1 {
    private long position = 0;
    private long depth = 0;

    void execute(Instruction instruction) {
        switch (instruction.getCommand()) {
            case FORWARD: position += instruction.getValue(); break;
            case UP: depth -= instruction.getValue(); break;
            case DOWN: depth += instruction.getValue(); break;
            default:
                throw new IllegalArgumentException("Invalid command: " + instruction);
        }
    }

    public long getPositionTimesDepth() {
        return position * depth;
    }
}
