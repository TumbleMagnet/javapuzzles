package be.rouget.puzzles.adventofcode.year2024.day06;

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


public class GuardGallivant {

    private static final Logger LOG = LogManager.getLogger(GuardGallivant.class);

    private final RectangleMap<LabChar> labMap;
    private final GuardPosition startPosition;

    private Set<Position> guardPositionsFromStep1;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(GuardGallivant.class);
        GuardGallivant aoc = new GuardGallivant(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public GuardGallivant(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        // Parse input into map of laboratory
        labMap = new RectangleMap<>(input, LabChar::parse);
        LOG.info("Lab dimensions: {} x {}", labMap.getWidth(), labMap.getHeight());

        // Compute start position of guard
        Position startLocation = labMap.getElements().stream()
                .filter(entry -> entry.getValue().equals(LabChar.GUARD))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
        startPosition = new GuardPosition(startLocation, Direction.UP);
        LOG.info("Start position: {}", startPosition);
    }

    public long computeResultForPart1() {

        // Move the guard until out of the map and record unique locations
        GuardPosition guardPosition = startPosition;
        Set<Position> uniqueLocations = Sets.newHashSet();
        while (labMap.isPositionInMap(guardPosition.position())) {
            uniqueLocations.add(guardPosition.position());
            guardPosition = moveGuard(labMap, guardPosition);
        }

        // Answer is the number of unique locations
        guardPositionsFromStep1 = uniqueLocations;
        return uniqueLocations.size();
    }

    private static GuardPosition moveGuard(RectangleMap<LabChar> map, GuardPosition currentPosition) {
        GuardPosition newPosition = currentPosition.advanceOneStep();
        if (!map.isPositionInMap(newPosition.position())) {
            // No obstacles outside the map
            return newPosition;
        }
        LabChar targetElement = map.getElementAt(newPosition.position());
        if (!LabChar.OBSTACLE.equals(targetElement)) {
            // No obstacle, move freely
            return newPosition;
        }
        // Rotate right and try advancing again
        return moveGuard(map, currentPosition.rotateRight());
    }

    public long computeResultForPart2() {

        // For every free space reached by the guard in step 1 (excluding the start location):
        // - build a new map by adding an obstacle at that position
        // - test whether this new map would cause the guard to be caught in a cycle (by moving the guard until he either
        //   walks out of the map or ends up in a past position)
        // - Return the number of maps which caused cycles
        List<Position> freeSpaces = guardPositionsFromStep1.stream()
                .filter(p -> labMap.getElementAt(p).equals(LabChar.FREE_SPACE))
                .toList();
        LOG.info("{} candidate free spaces for adding an obstacle", freeSpaces.size());
        return freeSpaces.stream()
                .filter(this::isACycleMap)
                .count();
    }

    private boolean isACycleMap(Position newObstacle) {

        // Modify map by adding the new obstacle
        labMap.setElementAt(newObstacle, LabChar.OBSTACLE);

        GuardPosition guardPosition = startPosition;
        Set<GuardPosition> pastPositions = Sets.newHashSet(startPosition);
        while (true) {
            guardPosition = moveGuard(labMap, guardPosition);
            if (!labMap.isPositionInMap(guardPosition.position())) {
                // Guard got out of the map, not a cycle
                // Restore the map and return false
                labMap.setElementAt(newObstacle, LabChar.FREE_SPACE);
                return false;
            }
            if (pastPositions.contains(guardPosition)) {
                // Guard came back to a past position + direction, so a cycle was detected
                // Restore the map and return false
                labMap.setElementAt(newObstacle, LabChar.FREE_SPACE);
                return true;
            }
            pastPositions.add(guardPosition);
        }
    }
}