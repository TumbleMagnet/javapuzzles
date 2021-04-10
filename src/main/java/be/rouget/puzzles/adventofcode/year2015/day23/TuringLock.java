package be.rouget.puzzles.adventofcode.year2015.day23;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class TuringLock {

    private static final String YEAR = "2015";
    private static final String DAY = "23";

    private static final Logger LOG = LogManager.getLogger(TuringLock.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TuringLock aoc = new TuringLock(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    private final List<Instruction> instructions;

    public TuringLock(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.instructions = input.stream()
                .map(Instruction::fromInput)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        Computer computer = new Computer(this.instructions);
        computer.execute();
        return computer.getRegister(Register.B);
    }

    public long computeResultForPart2() {
        Computer computer = new Computer(this.instructions);
        computer.setRegister(Register.A, 1);
        computer.execute();
        return computer.getRegister(Register.B);
    }
}