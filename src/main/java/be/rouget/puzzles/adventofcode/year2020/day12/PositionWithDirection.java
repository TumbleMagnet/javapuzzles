package be.rouget.puzzles.adventofcode.year2020.day12;

import lombok.Value;

@Value
public class PositionWithDirection {
    int x;
    int y;
    int direction; // In degrees with 0 = facing NORTH

    public int getManhattanDistanceFromPosition(int otherX, int otherY) {
        return Math.abs(otherX - this.getX()) + Math.abs(otherY - this.getY());
    }

    public PositionWithDirection applyInstruction(Instruction instruction) {
        switch (instruction.getAction()) {
            case NORTH:   return new PositionWithDirection(x, y + instruction.getValue(), direction);
            case EAST:    return new PositionWithDirection(x + instruction.getValue(), y, direction);
            case SOUTH:   return new PositionWithDirection(x, y - instruction.getValue(), direction);
            case WEST:    return new PositionWithDirection(x - instruction.getValue(), y, direction);
            case LEFT:    return new PositionWithDirection(x, y, normalizeDirection(direction - instruction.getValue()));
            case RIGHT:   return new PositionWithDirection(x, y, normalizeDirection(direction + instruction.getValue()));
            case FORWARD: return applyInstruction(new Instruction(currentDirectionToAction(), instruction.getValue()));
            default:
                throw new IllegalArgumentException("Unsupported instruction " + instruction);
        }
    }

    private Action currentDirectionToAction() {
        switch (direction) {
            case 0: return Action.NORTH;
            case 90: return Action.EAST;
            case 180: return Action.SOUTH;
            case 270: return Action.WEST;
            default:
                throw new IllegalStateException("Cannot convert current direction " + direction + " to an action code");
        }
    }

    private static int normalizeDirection(int direction) {
        while (direction < 0) {
            direction += 360;
        }
        return direction % 360;
    }
}
