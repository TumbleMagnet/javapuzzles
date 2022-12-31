package be.rouget.puzzles.adventofcode.year2022.day04;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CampCleanup {

    private static final Logger LOG = LogManager.getLogger(CampCleanup.class);
    private final List<AssignmentPair> assignmentPairs;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CampCleanup.class);
        CampCleanup aoc = new CampCleanup(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CampCleanup(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        assignmentPairs = input.stream()
                .map(AssignmentPair::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return assignmentPairs.stream()
                .filter(AssignmentPair::hasOneRangeContainedInTheOther)
                .count();
    }

    public long computeResultForPart2() {
        return assignmentPairs.stream()
                .filter(AssignmentPair::hasOverlappingRanges)
                .count();
    }
}