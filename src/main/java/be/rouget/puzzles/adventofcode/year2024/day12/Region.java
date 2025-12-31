package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;

import java.util.*;

public class Region {
    private final Plant plant;
    private final Set<Position> positions;

    public Region(Plant plant, Collection<Position> positions) {
        this.plant = plant;
        this.positions = new HashSet<>(positions);
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public long computePriceForPart1() {
        return computeArea() * computePerimeter();
    }

    public long computePriceForPart2() {
        return computeArea() * computeNumberOfSides();
    }

    public long computeNumberOfSides() {

        // Enumerate all individual outer edges of each plant in the region
        List<Side> outerEdges = positions.stream()
                .map(this::enumerateOuterEdges)
                .flatMap(List::stream)
                .toList();

        // Find pairs of edges that can be merged together into the same "side" (until no more merge can be found)
        List<Side> sides = mergeEdges(outerEdges);

        // Count remaining sides
        return sides.size();
    }

    private List<Side> enumerateOuterEdges(Position position) {
        return Arrays.stream(Direction.values())
                .filter(direction -> !positions.contains(position.getNeighbour(direction)))
                .map(direction -> toSide(position, direction))
                .toList();
    }

    private List<Side> mergeEdges(List<Side> outerEdges) {
        for (int i = 0; i < outerEdges.size(); i++) {
            for (int j = i+1; j < outerEdges.size(); j++) {
                Side first = outerEdges.get(i);
                Side second = outerEdges.get(j);
                Optional<Side> optionalMerge = Side.mergeSides(first, second);
                if (optionalMerge.isPresent()) {
                    // Merge found, continue to merge recursively the new list
                    // (built by excluding the elements which were merged and by adding the result of the merge)
                    List<Side> newEdges = buildNewEdges(outerEdges, i, j, optionalMerge.get());
                    return mergeEdges(newEdges);
                }
            }
        }
        // No merge found
        return outerEdges;
    }

    private static List<Side> buildNewEdges(List<Side> currentEdges, int firstIndexToExclude, int secondIndexToExclude, Side newEdge) {
        List<Side> newEdges = Lists.newArrayList(newEdge);
        for (int k = 0; k < currentEdges.size(); k++) {
            if (k != firstIndexToExclude && k != secondIndexToExclude) {
                newEdges.add(currentEdges.get(k));
            }
        }
        return newEdges;
    }

    private Side toSide(Position position, Direction direction) {
        return switch (direction) {
            case UP -> new Side(Direction.DOWN, new Position(position.getX(), position.getY()), new Position(position.getX()+1, position.getY()));
            case DOWN -> new Side(Direction.UP, new Position(position.getX(), position.getY()+1), new Position(position.getX()+1, position.getY()+1));
            case LEFT -> new Side(Direction.RIGHT, new Position(position.getX(), position.getY()), new Position(position.getX(), position.getY()+1));
            case RIGHT -> new Side(Direction.LEFT, new Position(position.getX()+1, position.getY()), new Position(position.getX()+1, position.getY()+1));
        };
    }

    public long computeArea() {
        return positions.size();
    }

    public long computePerimeter() {
        return positions.stream()
                .mapToLong(this::countOuterEdges)
                .sum();
    }

    private long countOuterEdges(Position position) {
        return Arrays.stream(Direction.values())
                .map(position::getNeighbour)
                .filter(neighbour -> !positions.contains(neighbour))
                .count();
    }

    public boolean contains(Plant plant, Position position) {
        return this.plant.equals(plant) && this.positions.contains(position);
    }
}
