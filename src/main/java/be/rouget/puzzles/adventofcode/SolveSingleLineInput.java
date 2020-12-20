package be.rouget.puzzles.adventofcode;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SolveSingleLineInput {

    private static Logger LOG = LogManager.getLogger(SolveSingleLineInput.class);

    private String input;

    public SolveSingleLineInput(String input) {
        this.input = input;
    }

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_dayXX_input.txt");
        SolveSingleLineInput aoc = new SolveSingleLineInput(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public int computeResultForPart1() {
        return -1;
    }
    public int computeResultForPart2() {
        return -1;
    }
}