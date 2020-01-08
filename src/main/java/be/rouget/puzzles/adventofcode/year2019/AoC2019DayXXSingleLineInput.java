package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019DayXXSingleLineInput {

    private static Logger LOG = LogManager.getLogger(AoC2019DayXXSingleLineInput.class);

    private String input;

    public AoC2019DayXXSingleLineInput(String input) {
        this.input = input;
    }

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_dayXX_input.txt");
        AoC2019DayXXSingleLineInput aoc = new AoC2019DayXXSingleLineInput(input);
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