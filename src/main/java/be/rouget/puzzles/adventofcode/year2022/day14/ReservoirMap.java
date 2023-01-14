package be.rouget.puzzles.adventofcode.year2022.day14;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ReservoirMap {
    
    // Stores "Rock" and "Sand" elements in the reservoir. No element for a position means it is filled of "Air".
    private final Map<Position, ReservoirItem> elements = Maps.newHashMap();
    
    // Maximum Y of all rock positions. Sand which reaches that Y will fall forever...
    private final int maxRockY;
    
    // Take floor into account
    private final boolean hasFloor;

    public ReservoirMap(List<RockPath> rockPaths, boolean hasFloor) {
        rockPaths.forEach(this::addRockPath);
        this.maxRockY = computeMaxRockY();
        this.hasFloor = hasFloor;
    }

    private void addRockPath(RockPath rockPath) {
        Position previous = null;
        for (Position current : rockPath.positions()) {
            if (previous != null) {
                addRockSegment(previous, current);
            }
            previous = current;
        }
    }

    private void addRockSegment(Position from, Position to) {
        if (from.getX() == to.getX()) {
            // Vertical segment
            int fromY = Math.min(from.getY(), to.getY());
            int toY = Math.max(from.getY(), to.getY());
            for (int y = fromY; y <= toY ; y++) {
                setElementAt(new Position(from.getX(), y), ReservoirItem.ROCK);
            }
        } else if (from.getY() == to.getY()) {
            // Horizontal segment
            int fromX = Math.min(from.getX(), to.getX());
            int toX = Math.max(from.getX(), to.getX());
            for (int x = fromX; x <= toX ; x++) {
                setElementAt(new Position(x, from.getY()), ReservoirItem.ROCK);
            }
        } else {
            throw new IllegalArgumentException("Invalid segment from " + from + " to " + to);
        }
    }

    private int computeMaxRockY() {
        return elements.entrySet().stream()
                .filter(e -> e.getValue() == ReservoirItem.ROCK)
                .mapToInt(e -> e.getKey().getY())
                .max()
                .orElseThrow();
    }

    public ReservoirItem getElementAt(Position position) {
        ReservoirItem solidItem = elements.get(position);
        if (solidItem != null) {
            return solidItem;
        } else if (hasFloor && position.getY() == getFloorY()) {
            // Floor
            return ReservoirItem.ROCK;
        } else {
            return ReservoirItem.AIR;
        }
    }

    private int getFloorY() {
        return maxRockY + 2;
    }

    public void setElementAt(Position position, ReservoirItem item) {
        elements.put(position, item);
    }

    /**
     * Adds a sand unit and returns true if it comes to rest.
     * @return true if the new sand unit comes to rest after being added or false if it flowed into the abyss
     */
    public boolean addSandUnit() {
        Position startPosition = new Position(500, 0);
        if (getElementAt(startPosition) != ReservoirItem.AIR) {
            // Flowing of sand is blocked
            return false;
        }
        Position sand = startPosition;
        while (true) {
            Position newPosition = moveSand(sand);
            if (newPosition == sand) {
                // Sand came to rest, add its final position to reservoir
                setElementAt(newPosition, ReservoirItem.SAND);
                return true;
            }
            if (isFallingForEver(newPosition)) {
                // Nothing to stop sand from falling forever
                return false;
            }
            // Move sand to new position
            sand = newPosition;
        }
    }

    private boolean isFallingForEver(Position position) {
        // Position is falling forever if there is no floor and it is already below the last rock
        return !hasFloor && position.getY() > this.maxRockY;
    }

    private Position moveSand(Position currentPosition) {
        return IntStream.of(0, -1, 1)
                .mapToObj(deltaX -> new Position(currentPosition.getX() + deltaX, currentPosition.getY() + 1))
                .filter(candidate -> getElementAt(candidate) == ReservoirItem.AIR)
                .findFirst()
                .orElse(currentPosition);
    }
}
