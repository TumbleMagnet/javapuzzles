package be.rouget.puzzles.adventofcode;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SolveMultilineInput {

    private static final String YEAR = "2020";
    private static final String DAY = "XX";

    private static Logger LOG = LogManager.getLogger(SolveMultilineInput.class);

    private List<String> input;

    public SolveMultilineInput(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SolveMultilineInput aoc = new SolveMultilineInput(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return -1;
    }
    public long computeResultForPart2() {
        return -1;
    }
}