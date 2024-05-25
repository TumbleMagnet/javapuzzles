package be.rouget.puzzles.adventofcode.year2023.day17;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class ClumsyCrucible {

    private static final Logger LOG = LogManager.getLogger(ClumsyCrucible.class);
    private final RectangleMap<HeatLossTile> heatLossMap;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ClumsyCrucible.class);
        ClumsyCrucible aoc = new ClumsyCrucible(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ClumsyCrucible(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        heatLossMap = new RectangleMap<>(input, HeatLossTile::parse);
    }

    public long computeResultForPart1() {
        HeatLossGraph graph = new HeatLossGraph(heatLossMap, false);
        return Dijkstra.shortestDistance(graph, graph.getStartState(), graph::isTargetState);
    }

    public long computeResultForPart2() {
        HeatLossGraph graph = new HeatLossGraph(heatLossMap, true);
        return Dijkstra.shortestDistance(graph, graph.getStartState(), graph::isTargetState);
    }
}