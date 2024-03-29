package be.rouget.puzzles.adventofcode.year2023.day12;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class HotSprings {

    private static final Logger LOG = LogManager.getLogger(HotSprings.class);
    
    private final List<ConditionRecord> conditionRecords;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(HotSprings.class);
        HotSprings aoc = new HotSprings(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public HotSprings(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        conditionRecords = input.stream()
                .map(ConditionRecord::parse)
                .toList();
        LOG.info("Found {} condition records...", conditionRecords.size());
    }

    public long computeResultForPart1() {
        return conditionRecords.stream()
                .mapToLong(ConditionRecord::countValidArrangements)
                .sum();
    }
    
    public long computeResultForPart2() {
        return conditionRecords.stream()
                .map(ConditionRecord::unfold)
                .mapToLong(ConditionRecord::countValidArrangements)
                .sum();
    }
}