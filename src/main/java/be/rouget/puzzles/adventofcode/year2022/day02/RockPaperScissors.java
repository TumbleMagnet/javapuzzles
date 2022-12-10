package be.rouget.puzzles.adventofcode.year2022.day02;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;


public class RockPaperScissors {

    private static final Logger LOG = LogManager.getLogger(RockPaperScissors.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(RockPaperScissors.class);
        RockPaperScissors aoc = new RockPaperScissors(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RockPaperScissors(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.input = input;
    }

    public long computeResultForPart1() {
        return computeScore(PlayWithOutcome::parseForPart1);
    }

    public long computeResultForPart2() {
        return computeScore(PlayWithOutcome::parseForPart2);
    }

    private long computeScore(Function<String, PlayWithOutcome> parseFunction) {
        return input.stream()
                .map(parseFunction)
                .mapToLong(PlayWithOutcome::getScore)
                .sum();
    }
}