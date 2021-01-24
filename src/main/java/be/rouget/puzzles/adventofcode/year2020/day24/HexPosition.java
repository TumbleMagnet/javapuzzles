package be.rouget.puzzles.adventofcode.year2020.day24;

import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class HexPosition {
    int x;
    int y;
    int z;

    public HexPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (x + y + z != 0) {
            throw new IllegalArgumentException("Invalid coordinates: x=" + x + ", y=" + y + ", z=" + z);
        }
    }

    public HexPosition move(HexDirection direction) {
        switch (direction) {
            case WEST:       return new HexPosition(x-1, y+1, z+0);
            case SOUTH_WEST: return new HexPosition(x-1, y+0, z+1);
            case SOUTH_EAST: return new HexPosition(x+0, y-1, z+1);
            case EAST:       return new HexPosition(x+1, y-1, z+0);
            case NORTH_EAST: return new HexPosition(x+1, y+0, z-1);
            case NORTH_WEST: return new HexPosition(x+0, y+1, z-1);
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }
    }

    public List<HexPosition> getNeighbours() {
        return Arrays.stream(HexDirection.values()).map(p -> this.move(p)).collect(Collectors.toList());
    }
}
