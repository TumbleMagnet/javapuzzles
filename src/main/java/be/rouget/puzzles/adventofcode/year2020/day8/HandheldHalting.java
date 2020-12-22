package be.rouget.puzzles.adventofcode.year2020.day8;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HandheldHalting {

    private static final String YEAR = "2020";
    private static final String DAY = "08";

    private static final Logger LOG = LogManager.getLogger(HandheldHalting.class);

    private List<String> input;

    public HandheldHalting(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        HandheldHalting aoc = new HandheldHalting(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        Handheld handheld = new Handheld();
        try {
            handheld.runProgram(input);
            return -1;
        } catch (InfiniteLoopDetected infiniteLoopDetected) {
            return handheld.getAccumulator();
        }
    }

    public long computeResultForPart2() {
        for (int i = 0; i < input.size(); i++) {
            List<String> modifiedProgram = changeInstructionAt(input, i);
            try {
                Handheld handheld = new Handheld();
                handheld.runProgram(modifiedProgram);
                return handheld.getAccumulator();
            } catch (InfiniteLoopDetected infiniteLoopDetected) {
                LOG.info("Modification of line {} did not work, continuing...", i);
            }
        }
        throw new IllegalStateException("No modification worked!");
    }

    private List<String> changeInstructionAt(List<String> input, int targetIndex) {
        List<String> modifiedInput = Lists.newArrayList();
        int index = 0;
        for (String line : input) {
            if (index == targetIndex) {
                if (line.startsWith("jmp")) {
                    modifiedInput.add(line.replace("jmp", "nop"));
                }
                else if (line.startsWith("nop")) {
                    modifiedInput.add(line.replace("nop", "jmp"));
                }
                else {
                    modifiedInput.add(line);
                }
            } else {
                modifiedInput.add(line);
            }
            index++;
        }
        return modifiedInput;
    }
}