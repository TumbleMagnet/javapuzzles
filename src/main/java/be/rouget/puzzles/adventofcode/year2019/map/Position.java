package be.rouget.puzzles.adventofcode.year2019.map;

import com.google.common.base.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(Direction d) {
        switch (d) {
            case UP: return new Position(x, y-1);
            case DOWN: return new Position(x, y+1);
            case LEFT: return new Position(x-1, y);
            case RIGHT: return new Position(x+1, y);
            default: throw new IllegalArgumentException("Unknown direction " + d);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + ']';
    }

}
