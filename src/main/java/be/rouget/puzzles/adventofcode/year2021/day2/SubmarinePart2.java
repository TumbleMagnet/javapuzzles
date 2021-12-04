package be.rouget.puzzles.adventofcode.year2021.day2;

public class SubmarinePart2 {
    private long position = 0;
    private long depth = 0;
    private long aim = 0;

    void execute(Instruction instruction) {
        switch (instruction.getCommand()) {
            case FORWARD: position += instruction.getValue(); depth += aim * instruction.getValue(); break;
            case UP: aim -= instruction.getValue(); break;
            case DOWN: aim += instruction.getValue(); break;
            default:
                throw new IllegalArgumentException("Invalid command: " + instruction);
        }
    }

    public long getPositionTimesDepth() {
        return position * depth;
    }
}
