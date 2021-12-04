package be.rouget.puzzles.adventofcode.year2021.day2;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class Dive {

    private static final String YEAR = "2021";
    private static final String DAY = "02";

    private static final Logger LOG = LogManager.getLogger(Dive.class);
    private final List<Instruction> instructions;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        Dive aoc = new Dive(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public Dive(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = input.stream()
                .map(Instruction::fromInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        SubmarinePart1 submarine = new SubmarinePart1();
        instructions.forEach(submarine::execute);
        return submarine.getPositionTimesDepth();
    }

    public long computeResultForPart2() {
        SubmarinePart2 submarine = new SubmarinePart2();
        instructions.forEach(submarine::execute);
        return submarine.getPositionTimesDepth();
    }
}