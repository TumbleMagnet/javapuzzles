package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019DayXXMultilineInput {

    private static Logger LOG = LogManager.getLogger(AoC2019DayXXMultilineInput.class);

    private List<String> input;

    public AoC2019DayXXMultilineInput(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_dayXX_input.txt");
        AoC2019DayXXMultilineInput aoc = new AoC2019DayXXMultilineInput(input);
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