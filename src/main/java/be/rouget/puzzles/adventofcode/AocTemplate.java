package be.rouget.puzzles.adventofcode;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AocTemplate {

    private static final String YEAR = "2020";
    private static final String DAY = "XX";

    private static final Logger LOG = LogManager.getLogger(AocTemplate.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AocTemplate aoc = new AocTemplate(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public AocTemplate(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
    }

    public long computeResultForPart1() {
        return -1;
    }
    public long computeResultForPart2() {
        return -1;
    }
}