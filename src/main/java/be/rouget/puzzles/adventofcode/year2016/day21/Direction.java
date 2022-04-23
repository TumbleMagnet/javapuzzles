package be.rouget.puzzles.adventofcode.year2016.day21;

public enum Direction {
    LEFT, RIGHT;

    public Direction getOpposite() {
        switch (this) {
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default:
                throw new IllegalStateException("Unsupported direction " + this);
        }
    }
}
