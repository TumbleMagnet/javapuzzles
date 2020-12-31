package be.rouget.puzzles.adventofcode.year2020.day14;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DockingData {

    private static final String YEAR = "2020";
    private static final String DAY = "14";

    private static final Logger LOG = LogManager.getLogger(DockingData.class);

    private final List<String> input;

    public DockingData(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        DockingData aoc = new DockingData(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        DecoderEmulator1 emulator = new DecoderEmulator1();
        input.forEach(emulator::executeInstruction);
        return emulator.sumOfMemories();
    }

    public long computeResultForPart2() {
        DecoderEmulator2 emulator = new DecoderEmulator2();
        input.forEach(emulator::executeInstruction);
        return emulator.sumOfMemories();
    }

}