package be.rouget.puzzles.adventofcode.year2024.day02;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class RedNosedReports {

    private static final Logger LOG = LogManager.getLogger(RedNosedReports.class);
    private final List<Report> reports;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(RedNosedReports.class);
        RedNosedReports aoc = new RedNosedReports(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RedNosedReports(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        reports = input.stream()
                .map(Report::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return reports.stream()
                .filter(Report::isSafe)
                .count();
    }

    public long computeResultForPart2() {
        return reports.stream()
                .filter(Report::isSafeWithDampener)
                .count();
    }
}