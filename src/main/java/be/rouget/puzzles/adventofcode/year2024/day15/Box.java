package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.map.Position;

public record Box(Position leftPosition, Position rightPosition) {

    public Box {
        if (leftPosition.getY() != rightPosition.getY()) {
            throw new IllegalArgumentException("Both parts of the boxes are not on the same line");
        }
        if (leftPosition.getX() + 1 != rightPosition.getX()) {
            throw new IllegalArgumentException("Both parts of the boxes are not in the right order");
        }
    }
}
