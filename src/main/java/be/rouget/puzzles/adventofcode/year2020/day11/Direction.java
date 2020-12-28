package be.rouget.puzzles.adventofcode.year2020.day11;

public enum Direction {
    NORTH_WEST (-1, -1),
    NORTH      (0, -1),
    NORTH_EAST (1, -1),
    WEST       (-1, 0),
    EAST       (1, 0),
    SOUTH_WEST (-1, 1),
    SOUTH      (0, 1),
    SOUTH_EAST (1, 1);

    private int deltaX;
    private int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
