package be.rouget.puzzles.adventofcode.year2023.day10;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class PipeMaze {

    private static final Logger LOG = LogManager.getLogger(PipeMaze.class);

    private final RectangleMap<PipeTile> pipeMap;
    private final Position startPosition;
    private final Set<Position> cyclePositions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PipeMaze.class);
        PipeMaze aoc = new PipeMaze(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PipeMaze(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        pipeMap = new RectangleMap<>(input, PipeTile::parse);
        startPosition = pipeMap.getElements().stream()
                .filter(element -> element.getValue() == PipeTile.START)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find start position"));
        LOG.info("Start position is {}", startPosition);
        cyclePositions = extractPipeCycle();
        LOG.info("Cycle length is {}", cyclePositions.size());

        // Replace start position with its actual pipe tile
        Set<Direction> startConnections = getPipesConnectedToStart().stream()
                .map(connection -> connection.fromDirection().reverse())
                .collect(Collectors.toSet());
        PipeTile startTile = PipeTile.fromConnections(startConnections);
        pipeMap.setElementAt(startPosition, startTile);
        LOG.info("Replaced tile at start position with {}...", startTile.getMapChar());
    }

    public long computeResultForPart1() {
        // The distance to the farthest point of the cycle is half of the cycle length
        return cyclePositions.size() / 2;
    }

    private Set<Position> extractPipeCycle() {
        // Extract all positions that are part of the cycle (including start)
        Set<Position> result = Sets.newHashSet(startPosition);

        // From start position, get one of the two cycle ends
        PipeCyclePosition current = getStartOfPipeCycle();
        LOG.info("Found start of pipe at {} with tile {}", current, pipeMap.getElementAt(current.position()).getMapChar());

        // Walk the cycle completely until we reach the start position again
        while (!current.position().equals(startPosition)) {
            result.add(current.position());
            current = nextPositionInCycle(current);
        }

        return result;
    }

    private PipeCyclePosition getStartOfPipeCycle() {
        Set<PipeCyclePosition> connections = getPipesConnectedToStart();
        if (connections.size() != 2) {
            throw new IllegalArgumentException("Expected 2 pipes connected to start position, got " + connections.size());
        }
        return connections.iterator().next();
    }

    private Set<PipeCyclePosition> getPipesConnectedToStart() {
        Set<PipeCyclePosition> connected = Sets.newHashSet();
        for (Direction directionFromStart : Direction.values()) {
            Position neighbourPosition = startPosition.getNeighbour(directionFromStart);
            if (pipeMap.isPositionInMap(neighbourPosition) && getConnectedNeighbours(neighbourPosition).contains(startPosition)) {
                // Found a pipe tile connected to the start position
                connected.add(new PipeCyclePosition(neighbourPosition, directionFromStart.reverse()));
            }
        }
        return connected;
    }

    private Set<Position> getConnectedNeighbours(Position pipePosition) {
        PipeTile tile = pipeMap.getElementAt(pipePosition);
        if (tile.getConnections().isEmpty()) {
            return Set.of();
        }
        return tile.getConnections().stream()
                .map(pipePosition::getNeighbour)
                .collect(Collectors.toSet());
    }

    private PipeCyclePosition nextPositionInCycle(PipeCyclePosition cyclePosition) {

        // Direction of exit is the direction other than the direction from entry
        PipeTile tile = pipeMap.getElementAt(cyclePosition.position());
        Direction exitDirection = tile.getConnections().stream()
                .filter(direction -> !direction.equals(cyclePosition.fromDirection()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find exit direction for position " + cyclePosition));

        Position neighbourPosition = cyclePosition.position().getNeighbour(exitDirection);
        return new PipeCyclePosition(neighbourPosition, exitDirection.reverse());
    }

    public long computeResultForPart2() {
        return pipeMap.getElements().stream()
                .map(Map.Entry::getKey)
                .filter(position -> !cyclePositions.contains(position)) // Consider only positions that are part of the cycle
                .filter(this::isWithinCycle)
                .count();
    }

    private boolean isWithinCycle(Position position) {
        // Detect whether a point is inside the cycle by counting how times it crosses cycle tiles in every 4 direction:
        // if odd in every direction, the point is inside the cycle.
        for (Direction direction : Direction.values()) {
            int numberOfIntersections = countIntersectionsWithCycle(position, direction);
            if (numberOfIntersections % 2 == 0) {
                // No or even number of intersections for this direction so position is not enclosed within the cycle
                return false;
            }
        }

        // Position has an odd number of intersections in all directions, so it is within the cycle
        return true;
    }

    private int countIntersectionsWithCycle(Position position, Direction direction) {
        int numberOfIntersections = 0;
        Position current = position.getNeighbour(direction);
        Direction intersectionStart = null;
        while (pipeMap.isPositionInMap(current)) {

            if (cyclePositions.contains(current)) {

                PipeTile cycleTile = pipeMap.getElementAt(current);
                Set<Direction> intersectingDirections = cycleTile.getIntersectingDirections(direction);
                if (intersectingDirections.size() == 2) {
                    // If clean intersection, increase count
                    numberOfIntersections++;
                } else if (intersectingDirections.size() == 1) {
                    Direction intersectingDirection = intersectingDirections.iterator().next();
                    if (intersectionStart == null) {
                        // if turn and start: store incoming direction
                        intersectionStart = intersectingDirection;
                    } else {
                        // if turn and end: compare outgoing direction with incoming direction
                        if (intersectionStart == intersectingDirection.reverse()) {
                            // - if opposite: increase count
                            numberOfIntersections++;
                        }
                        intersectionStart = null;
                    }
                }
            }
            current = current.getNeighbour(direction);
        }
        return numberOfIntersections;
    }
}