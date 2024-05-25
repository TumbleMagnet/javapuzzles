package be.rouget.puzzles.adventofcode.year2023.day17;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;

public record SearchState(Position position, Direction direction, int movesInStraightLine) {
    
    public SearchState turnLeft() {
        return new SearchState(position.getNeighbour(direction.turnLeft()), direction.turnLeft(), 1);
    }

    public SearchState turnRight() {
        return new SearchState(position.getNeighbour(direction.turnRight()), direction.turnRight(), 1);
    }

    public SearchState ahead() {
        return new SearchState(position.getNeighbour(direction), direction, movesInStraightLine + 1);
    }
}
