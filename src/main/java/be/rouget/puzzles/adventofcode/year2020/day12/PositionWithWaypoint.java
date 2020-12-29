package be.rouget.puzzles.adventofcode.year2020.day12;

import lombok.Value;

@Value
public class PositionWithWaypoint {
    int x;
    int y;
    Waypoint waypoint;

    public int getManhattanDistanceFromPosition(int otherX, int otherY) {
        return Math.abs(otherX - this.getX()) + Math.abs(otherY - this.getY());
    }

    public PositionWithWaypoint applyInstruction(Instruction instruction) {
        switch (instruction.getAction()) {
            case NORTH:
            case RIGHT:
            case LEFT:
            case WEST:
            case SOUTH:
            case EAST:
                return new PositionWithWaypoint(x, y, waypoint.applyInstruction(instruction));
            case FORWARD:
                return new PositionWithWaypoint(x + waypoint.getDeltaX() * instruction.getValue(), y + waypoint.getDeltaY() * instruction.getValue(), waypoint);
            default:
                throw new IllegalArgumentException("Unsupported instruction " + instruction);
        }
    }
}
