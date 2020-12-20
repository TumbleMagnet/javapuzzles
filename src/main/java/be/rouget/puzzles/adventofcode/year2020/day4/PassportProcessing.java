package be.rouget.puzzles.adventofcode.year2020.day4;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PassportProcessing {

    private static final String YEAR = "2020";
    private static final String DAY = "04";

    private static Logger LOG = LogManager.getLogger(PassportProcessing.class);

    private List<String> input;

    public PassportProcessing(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_" + YEAR + "_day" + DAY + "_input.txt");
        PassportProcessing aoc = new PassportProcessing(input);
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