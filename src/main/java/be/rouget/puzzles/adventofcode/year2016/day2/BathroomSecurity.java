package be.rouget.puzzles.adventofcode.year2016.day2;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BathroomSecurity {

    private static final String YEAR = "2016";
    private static final String DAY = "02";

    private static final Logger LOG = LogManager.getLogger(BathroomSecurity.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        BathroomSecurity aoc = new BathroomSecurity(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public BathroomSecurity(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public String computeResultForPart1() {
        return computeCode(new KeyPad(Key.KEY_5, KeyPad.PART1_KEYS));
    }

    public String computeResultForPart2() {
        return computeCode(new KeyPad(Key.KEY_5, KeyPad.PART2_KEYS));
    }

    private String computeCode(KeyPad keyPad) {
        String code = "";
        for (String line : input) {
            keyPad.doMoves(line);
            code += keyPad.getCurrentKey().getCharacter();
        }
        return code;
    }

}