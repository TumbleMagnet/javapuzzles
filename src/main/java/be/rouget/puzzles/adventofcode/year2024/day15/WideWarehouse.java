package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.annotations.VisibleForTesting;

import java.util.*;
import java.util.stream.Collectors;

public class WideWarehouse {

    private RectangleMap<WideWarehouseItem> map;
    private Position robotPosition;

    public WideWarehouse(RectangleMap<WarehouseItem> map) {
        Map<Position, WideWarehouseItem> wideElements = new HashMap<>();
        for (Map.Entry<Position, WarehouseItem> entry : map.getElements()) {
            int originalX = entry.getKey().getX();
            int originalY = entry.getKey().getY();
            Position left = new Position(originalX * 2, originalY);
            Position right = new Position(originalX * 2 + 1, originalY);
            WarehouseItem originalItem = entry.getValue();
            if (WarehouseItem.WALL.equals(originalItem)) {
                wideElements.put(left, WideWarehouseItem.WALL);
                wideElements.put(right, WideWarehouseItem.WALL);
            } else if (WarehouseItem.BOX.equals(originalItem)) {
                wideElements.put(left, WideWarehouseItem.BOX_LEFT);
                wideElements.put(right, WideWarehouseItem.BOX_RIGHT);
            } else if (WarehouseItem.EMPTY.equals(originalItem)) {
                wideElements.put(left, WideWarehouseItem.EMPTY);
                wideElements.put(right, WideWarehouseItem.EMPTY);
            } else if (WarehouseItem.ROBOT.equals(originalItem)) {
                wideElements.put(left, WideWarehouseItem.ROBOT);
                wideElements.put(right, WideWarehouseItem.EMPTY);
            } else {
                throw new IllegalArgumentException("Unexpected item: " + originalItem);
            }
        }
        this.map = new RectangleMap<>(map.getWidth() * 2, map.getHeight(), wideElements);

        this.robotPosition = this.map.getElements().stream()
                .filter(entry -> entry.getValue() == WideWarehouseItem.ROBOT)
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    public void moveRobot(Command command) {

        // Compute and validate target position
        Position target = computeTarget(robotPosition, command);
        if (!map.isPositionInMap(target)) {
            // Should not happen as the warehouse outer edge is all walls
            throw new IllegalStateException("Cannot move robot out of the map!");
        }

        // Check what is at the target position
        WideWarehouseItem targetElement = map.getElementAt(target);

        // If target is a well, the move is not possible
        if (targetElement == WideWarehouseItem.WALL) {
            // Move is not possible
            return;
        }

        // If target is a box, verify it can be pushed
        Optional<Box> optionalBox = getBoxAtPosition(target);
        if (optionalBox.isPresent()) {
            Box box = optionalBox.get();

            // We will need to move boxes around. As it is possible that we only realize that the complete move is
            // not possible after having already moved some boxes, we need a way to rollback to the initial state
            RectangleMap<WideWarehouseItem> mapBackup = new RectangleMap<>(map);
            try {
               moveBox(box, command);
            } catch (MoveFailedException _) {
                // Move failed after some boxes were moved, reset map and cancel the robot move
                this.map = mapBackup;
                return;
            }
        }

        // Target is free, move the robot
        map.setElementAt(robotPosition, WideWarehouseItem.EMPTY);
        map.setElementAt(target, WideWarehouseItem.ROBOT);
        this.robotPosition = target;
    }

    private void moveBox(Box box, Command command) throws MoveFailedException {

        // Compute the new target position(s) that the box will need to occupy
        // - LEFT/RIGHT: only one new position on the left/right
        // - UP/DOWN: two new positions
        List<Position> targetPositions = switch (command) {
            case LEFT -> List.of(box.leftPosition().getNeighbour(Direction.LEFT));
            case RIGHT -> List.of(box.rightPosition().getNeighbour(Direction.RIGHT));
            case UP -> List.of(
                    box.leftPosition().getNeighbour(Direction.UP),
                    box.rightPosition().getNeighbour(Direction.UP)
            );
            case DOWN -> List.of(
                    box.leftPosition().getNeighbour(Direction.DOWN),
                    box.rightPosition().getNeighbour(Direction.DOWN)
            );
        };

        // Validate target positions are still in the map (should be OK since the map borders are walls)
        targetPositions.stream()
                .filter(p -> !map.isPositionInMap(p))
                .forEach(p -> {
                    throw new IllegalStateException("Target position is out side the map: " + p);
                });

        // If any of target positions is a wall, move is not possible
        boolean foundAWall = targetPositions.stream()
                .map(p -> map.getElementAt(p))
                .anyMatch(e -> e == WideWarehouseItem.WALL);
        if (foundAWall) {
            // Move is not possible
            throw new MoveFailedException();
        }

        // Collect the set of boxes that occupy the target positions and move them away
        Set<Box> boxesToPush = targetPositions.stream()
                .map(this::getBoxAtPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        for (Box boxToPush : boxesToPush) {
            moveBox(boxToPush, command);
        }

        // Move the box
        map.setElementAt(box.leftPosition(), WideWarehouseItem.EMPTY);
        map.setElementAt(box.rightPosition(), WideWarehouseItem.EMPTY);
        map.setElementAt(computeTarget(box.leftPosition(), command), WideWarehouseItem.BOX_LEFT);
        map.setElementAt(computeTarget(box.rightPosition(), command), WideWarehouseItem.BOX_RIGHT);
    }

    private Optional<Box> getBoxAtPosition(Position position) {
        WideWarehouseItem element = map.getElementAt(position);
        return switch (element) {
            case BOX_LEFT -> Optional.of(new Box(position, position.getNeighbour(Direction.RIGHT)));
            case BOX_RIGHT -> Optional.of(new Box(position.getNeighbour(Direction.LEFT), position));
            default -> Optional.empty();
        };
    }

    private Position computeTarget(Position start, Command command) {
        return switch (command) {
            case UP -> start.getNeighbour(Direction.UP);
            case DOWN -> start.getNeighbour(Direction.DOWN);
            case LEFT -> start.getNeighbour(Direction.LEFT);
            case RIGHT -> start.getNeighbour(Direction.RIGHT);
        };
    }

    public long computeSumOfBoxGpsCoordinates() {
        return map.getElements().stream()
                .filter(entry -> entry.getValue() == WideWarehouseItem.BOX_LEFT)
                .map(Map.Entry::getKey)
                .mapToLong(this::toGpsCoordinate)
                .sum();
    }

    private long toGpsCoordinate(Position position) {
        return position.getY() * 100L + position.getX();
    }

    @VisibleForTesting
    public RectangleMap<WideWarehouseItem> getMap() {
        return map;
    }

    @VisibleForTesting
    public Position getRobotPosition() {
        return robotPosition;
    }
}
