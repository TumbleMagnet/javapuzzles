package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.year2022.day16.fullgraph.*;
import be.rouget.puzzles.adventofcode.year2022.day16.reducedgraph.ReducedGraph;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProboscideaVolcanium {

    public static final int MAX_TIME = 30;
    public static final int MAX_TIME_PART2 = MAX_TIME - 4;
    public static final String NAME_OF_STARTING_POSITION = "AA";

    private static final Logger LOG = LogManager.getLogger(ProboscideaVolcanium.class);

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ProboscideaVolcanium.class);
        ProboscideaVolcanium aoc = new ProboscideaVolcanium(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ProboscideaVolcanium(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        Valves.initializeValves(input);
    }

    public long computeResultForPart1() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long result1 = PressureLossGraph.computeResultForPart1(MAX_TIME, NAME_OF_STARTING_POSITION);
        LOG.info("Got result {} with complete graph and dijkstra in {} ms", result1, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        stopwatch.stop().reset().start();
        long result2 = ReducedGraph.computeResultForPart1(MAX_TIME, NAME_OF_STARTING_POSITION);
        LOG.info("Got result {} with reduced graph in {} ms", result2, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result2;
    }

    public long computeResultForPart2() {
        return PressureLossGraphPart2.computeResultForPart2(MAX_TIME_PART2, NAME_OF_STARTING_POSITION);
    }
}