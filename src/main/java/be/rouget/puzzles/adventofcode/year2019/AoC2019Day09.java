package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AoC2019Day09 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day09.class);

    public static void main(String[] args) {
        String input = ResourceUtils.readIntoString("aoc_2019_day09_input.txt");
        int result = computeResult(input);
    }

    public static int computeResult(String input) {

        Computer computer = new Computer(input);
        computer.run(2);
        computer.getOutput().stream().forEach(System.out::println);

        return -1;
    }
}