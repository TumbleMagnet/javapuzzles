package be.rouget.puzzles.adventofcode.year2020.day3;

public class Slope {
    private int deltaX;
    private int deltaY;

    public Slope(int deltaX, int deltaY) {
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
