package be.rouget.puzzles.adventofcode.year2022.day17;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;

import java.util.Set;
import java.util.stream.Collectors;

public record Rock(Position position, Shape shape) {

    public Rock move(Direction direction) {
        return new Rock(position.getNeighbour(direction), shape);
    }
    
    public Set<Position> getFilledPositions() {
        return shape.getFilledPositions().stream()
                .map(shapePosition -> new Position(position.getX() + shapePosition.getX(), position.getY() + shapePosition.getY()))
                .collect(Collectors.toSet());
    }
}
