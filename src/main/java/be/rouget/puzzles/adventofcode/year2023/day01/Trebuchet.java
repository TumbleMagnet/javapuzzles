package be.rouget.puzzles.adventofcode.year2023.day01;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Trebuchet {

    private static final Logger LOG = LogManager.getLogger(Trebuchet.class);
    
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(Trebuchet.class);
        Trebuchet aoc = new Trebuchet(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public Trebuchet(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return input.stream()
                .mapToInt(Calibration::extractCalibrationPart1)
                .sum();
    }

    public long computeResultForPart2() {
        return input.stream()
                .mapToInt(Calibration::extractCalibrationPart2)
                .sum();
    }
}