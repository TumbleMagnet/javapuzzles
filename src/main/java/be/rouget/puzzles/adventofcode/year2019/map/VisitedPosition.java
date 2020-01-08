package be.rouget.puzzles.adventofcode.year2019.map;

import com.google.common.base.Objects;

public class VisitedPosition {
    Position position;
    int depth;

    public VisitedPosition(Position position, int depth) {
        this.position = position;
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitedPosition that = (VisitedPosition) o;
        return Objects.equal(position, that.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    public Position getPosition() {
        return position;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "VisitedPosition{" +
                "position=" + position +
                ", depth=" + depth +
                '}';
    }
}
