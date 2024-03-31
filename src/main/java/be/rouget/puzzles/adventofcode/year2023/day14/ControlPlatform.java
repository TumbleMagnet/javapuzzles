package be.rouget.puzzles.adventofcode.year2023.day14;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.*;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.year2023.day14.PlatformTile.EMPTY;
import static be.rouget.puzzles.adventofcode.year2023.day14.PlatformTile.ROUND_ROCK;

public class ControlPlatform {
    private final RectangleMap<PlatformTile> platform;

    public ControlPlatform(List<String> input) {
        this.platform = new RectangleMap<>(input, PlatformTile::parse);
    }
    
    public void title(Direction direction) {
        // Move all round rock in the tile direction until they cannot move anymore
        // Start with the edge corresponding to the direction of the tile and work backward
        for (Position start : extractSortedPositionsOfRoundRocks(getComparatorForTileDirection(direction))) {
            Optional<Position> possibleTarget = findTargetPosition(start, direction);
            if (possibleTarget.isPresent()) {
                Position target = possibleTarget.get();
                platform.setElementAt(start, EMPTY);
                platform.setElementAt(target, ROUND_ROCK);
            }
        }
    }

    public long computeLoad() {
        return positionsOfRoundRocks().stream()
                .mapToLong(position -> platform.getHeight() - position.getY())
                .sum();
    }

    public Set<Position> positionsOfRoundRocks() {
        return platform.getElements().stream()
                .filter(entry -> entry.getValue() == ROUND_ROCK)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private List<Position> extractSortedPositionsOfRoundRocks(Comparator<Position> comparator) {
        return positionsOfRoundRocks().stream()
                .sorted(comparator)
                .toList();
    }

    private static Comparator<Position> getComparatorForTileDirection(Direction direction) {
        return switch (direction) {
            // When tiling UP, process rows to top to bottom 
            case UP -> Comparator.comparing(Position::getY).thenComparing(Position::getX);

            // When tiling DOWN, process rows to bottom to top 
            case DOWN -> Comparator.comparing(Position::getY).reversed().thenComparing(Position::getX);

            // When tiling RIGHT, process columns from right to left
            case RIGHT -> Comparator.comparing(Position::getX).reversed().thenComparing(Position::getY);

            // When tiling LEFT, process columns from left to right
            case LEFT -> Comparator.comparing(Position::getX).thenComparing(Position::getY);
        };
    }

    private Optional<Position> findTargetPosition(Position start, Direction direction) {
        Position target = null;
        Position candidate = start.getNeighbour(direction);
        while (platform.isPositionInMap(candidate) && platform.getElementAt(candidate).equals(EMPTY)) {
            target = candidate;
            candidate = candidate.getNeighbour(direction);
        }
        return Optional.ofNullable(target);
    }
}
