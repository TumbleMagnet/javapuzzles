package be.rouget.puzzles.adventofcode.year2024.day13;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public class ClawContraption {

    public static final BigInteger COORDINATE_FIX = BigInteger.valueOf(10000000000000L);
    private static final Logger LOG = LogManager.getLogger(ClawContraption.class);
    private final List<ClawMachine> machines;

    @SuppressWarnings("java:S2629") // OK to compute results when logging
    static void main() {
        List<String> input = SolverUtils.readInput(ClawContraption.class);
        ClawContraption aoc = new ClawContraption(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ClawContraption(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        machines = AocStringUtils.extractParagraphs(input).stream()
                .map(ClawMachine::parse)
                .toList();
        LOG.info("Found {} machines...", machines.size());
    }

    public long computeResultForPart1() {
        return machines.stream()
                .map(ClawMachine::computeCostToPrizeForPart1)
                .flatMap(Optional::stream)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public long computeResultForPart2() {
        return machines.stream()
                .map(this::fixMachineForPart2)
                .map(ClawMachine::computeCostToPrizeForPart2)
                .flatMap(Optional::stream)
                .mapToLong(Long::longValue)
                .sum();
    }

    private ClawMachine fixMachineForPart2(ClawMachine original) {
        return new ClawMachine(
                original.buttonA(),
                original.buttonB(),
                new BigPosition(
                        original.prize().x().add(COORDINATE_FIX),
                        original.prize().y().add(COORDINATE_FIX))
        );
    }
}