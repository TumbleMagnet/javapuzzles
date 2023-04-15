package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
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
    private final BitSetReducedGraph reducedGraph;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ProboscideaVolcanium.class);
        ProboscideaVolcanium aoc = new ProboscideaVolcanium(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ProboscideaVolcanium(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        reducedGraph = new BitSetReducedGraph(input, NAME_OF_STARTING_POSITION);
    }

    public long computeResultForPart1() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long result = reducedGraph.computeResultForPart1(MAX_TIME);
        LOG.info("Got result {} in {} ms", result, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    public long computeResultForPart2() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long result = reducedGraph.computeResultForPart2(MAX_TIME_PART2);
        LOG.info("Part 2: got result {} in {} ms", result, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }
}