package be.rouget.puzzles.adventofcode.year2021.day23;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Amphipod {

    private static final Logger LOG = LogManager.getLogger(Amphipod.class);

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(Amphipod.class);
        Amphipod aoc = new Amphipod(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public Amphipod(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
    }

    public long computeResultForPart1() {
        return -1;
    }
    public long computeResultForPart2() {
        return -1;
    }
}