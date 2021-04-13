package be.rouget.puzzles.adventofcode.year2016.day1;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;

@Value
public class BlockPosition {
    Position position;
    Direction direction;

    public BlockPosition followInstruction(Instruction instruction) {
        List<BlockPosition> positions = followInstructionStepByStep(instruction);
        return positions.get(positions.size() - 1);
    }

    public List<BlockPosition> followInstructionStepByStep(Instruction instruction) {
        Direction newDirection = instruction.getRotation() == Rotation.LEFT ? direction.turnLeft() : direction.turnRight();
        List<BlockPosition> newPositions = Lists.newArrayList();
        Position newPosition = position;
        for (int i = 0; i < instruction.getDistance(); i++) {
            newPosition = move(newPosition, newDirection, 1);
            newPositions.add(new BlockPosition(newPosition, newDirection));
        }
        return newPositions;
    }

    public int distanceFrom(BlockPosition other) {
        return computeDistance(position, other.getPosition());
    }

    private static Position move(Position position, Direction direction, int distance) {
        switch (direction) {
            case UP: return new Position(position.getX(), position.getY()-distance);
            case DOWN: return new Position(position.getX(), position.getY()+distance);
            case LEFT: return new Position(position.getX()-distance, position.getY());
            case RIGHT: return new Position(position.getX()+distance, position.getY());
            default: throw new IllegalArgumentException("Unknown direction " + direction);
        }
    }

    public static int computeDistance(Position p1, Position p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
}
