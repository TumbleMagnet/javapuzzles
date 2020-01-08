package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.AsciiComputer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019Day21 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day21.class);

    private String program;

    public AoC2019Day21(String input) {
        program = input;
    }

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day21_input.txt");
        AoC2019Day21 aoc = new AoC2019Day21(input);
        LOG.info("Result for part 1 is " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        AsciiComputer computer = new AsciiComputer(program);
        List<String> springscript = Lists.newArrayList(
                "NOT A J",
                "NOT C T",
                "AND D T",
                "OR T J",
                "WALK"
        );
        ComputerState state = computer.run(springscript);
        LOG.info("Computer state is: " + state);
        List<String> output = computer.getOutput();
        LOG.info("Output is: ");
        output.forEach(System.out::println);
        return Long.parseLong(output.get(output.size()-1));
    }

    public long computeResultForPart2() {

        AsciiComputer computer = new AsciiComputer(program);
        List<String> springscript = Lists.newArrayList(
                "NOT C J",
                "AND D J",
                "AND H J",
                "NOT B T",
                "AND D T",
                "OR T J",
                "NOT A T",
                "OR T J",
                "RUN"
        );
        ComputerState state = computer.run(springscript);
        LOG.info("Computer state is: " + state);
        List<String> output = computer.getOutput();
        LOG.info("Output is: ");
        output.forEach(System.out::println);
        return Long.parseLong(output.get(output.size()-1));
    }
}