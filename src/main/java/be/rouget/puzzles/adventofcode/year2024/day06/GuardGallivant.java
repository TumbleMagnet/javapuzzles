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

        // Compute start position of guard
        Position startLocation = labMap.getElements().stream()
                .filter(entry -> entry.getValue().equals(LabChar.GUARD))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
        startPosition = new GuardPosition(startLocation, Direction.UP);
    }

    public long computeResultForPart1() {

        // Move the guard until out of the map and record unique locations
        GuardPosition guardPosition = startPosition;
        Set<Position> uniqueLocations = Sets.newHashSet();
        while (labMap.isPositionInMap(guardPosition.position())) {
            uniqueLocations.add(guardPosition.position());
            guardPosition = nextPosition(guardPosition);
        }

        // Answer is the number of unique locations
        return uniqueLocations.size();
    }

    private GuardPosition nextPosition(GuardPosition currentPosition) {
        GuardPosition newPosition = currentPosition.advanceOneStep();
        if (!labMap.isPositionInMap(newPosition.position())) {
            // No obstacles outside the map
            return newPosition;
        }
        LabChar targetElement = labMap.getElementAt(newPosition.position());
        if (!LabChar.OBSTACLE.equals(targetElement)) {
            // No obstacle, move freely
            return newPosition;
        }
        // Rotate right and try advancing again
        return nextPosition(currentPosition.rotateRight());
    }

    public long computeResultForPart2() {
        return -1;
    }
}