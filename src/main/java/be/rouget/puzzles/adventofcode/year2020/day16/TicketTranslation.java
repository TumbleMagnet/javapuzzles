package be.rouget.puzzles.adventofcode.year2020.day16;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TicketTranslation {

    private static final String YEAR = "2020";
    private static final String DAY = "16";

    private static final Logger LOG = LogManager.getLogger(TicketTranslation.class);

    private final List<String> input;

    public TicketTranslation(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TicketTranslation aoc = new TicketTranslation(input);
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