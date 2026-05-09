package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.annotations.VisibleForTesting;

import java.util.Map;

public class Warehouse {

    private final RectangleMap<WarehouseItem> map;
    private Position robotPosition;

    public Warehouse(RectangleMap<WarehouseItem> map) {
        // Clone the map into one that we can freely modify
        this.map = new RectangleMap<>(map);

        this.robotPosition = this.map.getElements().stream()
                .filter(entry -> entry.getValue() == WarehouseItem.ROBOT)
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    public void moveRobot(Command command) {
        moveItem(robotPosition, command);
    }

    private boolean moveItem(Position position, Command command) {
        WarehouseItem element = map.getElementAt(position);
        if (element == WarehouseItem.EMPTY) {
            // OK, nothing to move
            return true;
        }
        if (element == WarehouseItem.WALL) {
            // Never possible
            return false;
        }

        // Compute and validate target position
        Position target = move(position, command);
        if (!map.isPositionInMap(target)) {
            // Should not happen as the warehouse outer edge is all walls
            return false;
        }

        // Tentatively push the item on the target position
        boolean targetIsFree = moveItem(target, command);
        if (!targetIsFree) {
            // There is an item in target position and it cannot be moved
            return false;
        }

        // Do the move
        map.setElementAt(target, element);
        map.setElementAt(position, WarehouseItem.EMPTY);
        if (element == WarehouseItem.ROBOT) {
            this.robotPosition = target;
        }
        return true;
    }


    private Position move(Position start, Command command) {
        return switch (command) {
            case UP -> start.getNeighbour(Direction.UP);
            case DOWN -> start.getNeighbour(Direction.DOWN);
            case LEFT -> start.getNeighbour(Direction.LEFT);
            case RIGHT -> start.getNeighbour(Direction.RIGHT);
        };
    }

    public long computeSumOfBoxGpsCoordinates() {
        return map.getElements().stream()
                .filter(entry -> entry.getValue() == WarehouseItem.BOX)
                .map(Map.Entry::getKey)
                .mapToLong(this::toGpsCoordinate)
                .sum();
    }

    private long toGpsCoordinate(Position position) {
        return position.getY() * 100L + position.getX();
    }

    @VisibleForTesting
    public RectangleMap<WarehouseItem> getMap() {
        return map;
    }

    @VisibleForTesting
    public Position getRobotPosition() {
        return robotPosition;
    }
}
