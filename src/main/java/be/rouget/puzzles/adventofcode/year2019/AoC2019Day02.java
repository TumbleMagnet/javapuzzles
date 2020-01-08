package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AoC2019Day02 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day02.class);

    public static void main(String[] args) {
        String input = ResourceUtils.readIntoString("aoc_2019_day02_input.txt");
        for (int noun =0; noun <100; noun++) {
            for (int verb =0; verb <100; verb++) {
                Computer computer = new Computer(input);
                computer.setMemoryAtPosition(1, noun);
                computer.setMemoryAtPosition(2, verb);
                computer.run();
                if (computer.getMemoryAtPosition(0) == 19690720) {
                    int result = 100 * noun + verb;
                    LOG.info("Result: " + result);
                    LOG.info("Parameters: " + noun + "," + verb);
                    System.exit(0);
                }
            }
        }
    }
}