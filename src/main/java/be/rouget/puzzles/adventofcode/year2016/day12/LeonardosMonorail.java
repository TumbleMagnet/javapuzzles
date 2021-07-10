package be.rouget.puzzles.adventofcode.year2016.day12;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class LeonardosMonorail {

    private static final String YEAR = "2016";
    private static final String DAY = "12";

    private static final Logger LOG = LogManager.getLogger(LeonardosMonorail.class);
    private final List<Instruction> instructions;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        LeonardosMonorail aoc = new LeonardosMonorail(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public LeonardosMonorail(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        instructions = input.stream()
                .map(Instruction::parseInstruction)
                .collect(Collectors.toList());
        instructions.forEach(LOG::info);
    }

    public long computeResultForPart1() {
        BunnyComputer computer = new BunnyComputer(instructions);
        computer.run();
        return computer.getRegisterValue("a");
    }

    public long computeResultForPart2() {
        BunnyComputer computer = new BunnyComputer(instructions, 0, 0, 1, 0);
        computer.run();
        return computer.getRegisterValue("a");
    }
}