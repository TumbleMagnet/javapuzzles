package be.rouget.puzzles.adventofcode.year2020.day12;

import lombok.Value;

import java.util.logging.XMLFormatter;

@Value
public class Waypoint {
    int deltaX;
    int deltaY;

    public Waypoint applyInstruction(Instruction instruction) {
        switch (instruction.getAction()) {
            case NORTH:   return new Waypoint(deltaX, deltaY + instruction.getValue());
            case EAST:    return new Waypoint(deltaX + instruction.getValue(), deltaY);
            case SOUTH:   return new Waypoint(deltaX, deltaY - instruction.getValue());
            case WEST:    return new Waypoint(deltaX - instruction.getValue(), deltaY);
            case LEFT:    return rotateLeft(instruction.getValue());
            case RIGHT:   return rotateRight(instruction.getValue());
            default:
                throw new IllegalArgumentException("Unsupported waypoint instruction " + instruction);
        }
    }

    public Waypoint rotateRight(int value) {
        switch (value) {
            case 0: return new Waypoint(deltaX, deltaY);
            case 90: return rotate90Right();
            case 180: return rotate90Right().rotate90Right();
            case 270: return rotate90Right().rotate90Right().rotate90Right();
            default:
                throw new IllegalStateException("Cannot handle rotation value " + value);
        }
    }

    public Waypoint rotateLeft(int value) {
        return rotateRight(360 - value);
    }

    public Waypoint rotate90Right() {
        return new Waypoint(deltaY, -deltaX);
    }
}
