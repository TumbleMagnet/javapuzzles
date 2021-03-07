package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.PermutationGenerator;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class AoC2019Day07 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day07.class);

    public static void main(String[] args) {

        String program = ResourceUtils.readIntoString("aoc_2019_day07_input.txt");

//        // Part 1
//        PermutationGenerator generator = new PermutationGenerator(Lists.newArrayList(0,1,2,3,4));
//        int maxPower = generator.generatePermutations().stream().mapToInt(p -> computePower(program, p)).max().orElseThrow(IllegalStateException::new);
//        LOG.info("Maximum power: " + maxPower);

        // Part 2
        PermutationGenerator<Integer> generator2 = new PermutationGenerator<Integer>(Lists.newArrayList(5,6,7,8,9));
        int maxPower2 = generator2.generatePermutations().stream().mapToInt(p -> computePowerWithFeedback(program, p)).max().orElseThrow(IllegalStateException::new);
        LOG.info("Maximum power with feedback: " + maxPower2);
    }

    public static int computePower(String program, List<Integer> phases) {
        LOG.info("Computing power for phases " + phases);
        int power = 0;
        for (int i=0; i<5; i++) {
            LOG.info("Starting step " + i);
            Computer computer = new Computer(program);
            computer.run(phases.get(i), power);
            List<String> output = computer.getOutput();
            if (output.size() != 1) {
                throw new IllegalStateException("Unexpected output size " + output.size());
            }
            power = Integer.parseInt(output.get(0));
            LOG.info("Power is " + power);
        }

        return power;
    }

    public static int computePowerWithFeedback(String program, List<Integer> phases) {
        LOG.info("Computing power for phases " + phases);

        // Initialize computers
        Computer[] computers = new Computer[5];
        for (int i = 0; i < 5; i++) {
            computers[i] = new Computer(program);
        }

        int power = 0;
        boolean isFinished = false;
        int iteration = 1;
        while (!isFinished) {
            for (int i=0; i<5; i++) {
                LOG.info("Starting iteration " + iteration + " step " + i);
                long[] args = null;
                if (iteration == 1) {
                    args = new long[] { phases.get(i), power };
                }
                else {
                    args = new long[] { power };
                }
                ComputerState state = computers[i].run(args);

                List<String> output = computers[i].getOutput();
                if (output.size() != 1) {
                    throw new IllegalStateException("Unexpected output size " + output.size());
                }
                power = Integer.parseInt(output.get(0));
                LOG.info("Power is " + power);

                // If last computer, check whether it has halted
                if ((i == 4) && (state == ComputerState.HALTED)) {
                    isFinished = true;
                }
            }
            iteration++;
        }

        return power;
    }
}