package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Predicate;


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
        ElevationGraph graph = new ElevationGraph(elevationMap, MoveDirection.FORWARD);
        Position startPosition = elevationMap.getStart();
        Predicate<Position> endPredicate = position -> elevationMap.getElementAt(position).isEnd();
        return Dijkstra.shortestDistance(graph, startPosition, endPredicate);
    }

    public long computeResultForPart2() {
        // Start from end and find the best starting point (the first point with minimal elevation)
        ElevationGraph graph = new ElevationGraph(elevationMap, MoveDirection.BACKWARD);
        Position startPosition = elevationMap.getEnd();
        Predicate<Position> endPredicate = position -> elevationMap.getElementAt(position).getElevation() == MapHeight.MIN_ELEVATION;
        return Dijkstra.shortestDistance(graph, startPosition, endPredicate);
    }
    
}