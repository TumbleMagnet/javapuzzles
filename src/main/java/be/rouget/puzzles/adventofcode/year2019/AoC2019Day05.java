package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class AoC2019Day05 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day05.class);

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day05_input.txt");
        Computer computer = new Computer(input);
        computer.run(5);
        LOG.info("Ouput: ");
        for (String s : computer.getOutput()) {
            LOG.info(s);
        }
    }

}