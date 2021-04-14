package be.rouget.puzzles.adventofcode.year2016.day2;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BathroomSecurity {

    private static final String YEAR = "2016";
    private static final String DAY = "02";
    private static final Logger LOG = LogManager.getLogger(BathroomSecurity.class);

    private static final List<String> KEYMAP_PART1 = List.of(
            "123",
            "456",
            "789"
    );
    private static final List<String> KEYMAP_PART2 = List.of(
            "..1..",
            ".234.",
            "56789",
            ".ABC.",
            "..D.."
    );

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
        return computeCode(new KeyMap(KEYMAP_PART1));
    }

    public String computeResultForPart2() {
        return computeCode(new KeyMap(KEYMAP_PART2));
    }

    private String computeCode(KeyMap keyMap) {
        String code = "";
        for (String line : input) {
            keyMap.doMoves(line);
            code += keyMap.getCurrentKey().getMapChar();
        }
        return code;
    }
}