package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class HillClimbingAlgorithm {

    private static final Logger LOG = LogManager.getLogger(HillClimbingAlgorithm.class);
    private final ElevationMap elevationMap;
    
    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(HillClimbingAlgorithm.class);
        HillClimbingAlgorithm aoc = new HillClimbingAlgorithm(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public HillClimbingAlgorithm(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        elevationMap = new ElevationMap(input);
        LOG.info("Map is {} by {}", elevationMap.getWidth(), elevationMap.getHeight());
        LOG.info("Start position: {}", elevationMap.getStart());
        LOG.info("End position: {}", elevationMap.getEnd());
    }

    public long computeResultForPart1() {
        return getShortestDistanceToEnd(elevationMap.getStart()).orElseThrow();
    }

    private Optional<Integer> getShortestDistanceToEnd(Position start) {
        try {
            return Optional.of(Dijkstra.shortestDistance(elevationMap, start, position -> elevationMap.getElementAt(position).isEnd()));
        } catch (IllegalStateException e) {
            // No path to destination was found
            return Optional.empty();
        }
    }

    public long computeResultForPart2() {

        List<Position> startingPositions = elevationMap.getElements().stream()
                .filter(element -> element.getValue().getElevation() == MapHeight.MIN_ELEVATION)
                .map(Map.Entry::getKey)
                .toList();
        LOG.info("Found {} possible starting points...", startingPositions.size());

        return startingPositions.stream()
                .map(this::getShortestDistanceToEnd)
                .filter(Optional::isPresent)
                .mapToInt(Optional::get)
                .min().orElseThrow();
    }
}