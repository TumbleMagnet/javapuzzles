package be.rouget.puzzles.adventofcode.year2020.day11;

import lombok.Value;

@Value
public class Position {
    int x;
    int y;

    public Position next(Direction direction) {
        return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
    }
}
