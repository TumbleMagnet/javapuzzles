package be.rouget.puzzles.adventofcode.year2024.day06;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;

public record GuardPosition(Position position, Direction direction) {

    public GuardPosition advanceOneStep() {
        Position newLocation = position().getNeighbour(direction());
        return new GuardPosition(newLocation, direction());
    }

    public GuardPosition rotateRight() {
        return new GuardPosition(position(), direction().turnRight());
    }
}
