package be.rouget.puzzles.adventofcode.year2023.day15;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class LensLibrary {

    private static final Logger LOG = LogManager.getLogger(LensLibrary.class);
    
    private final List<Instruction> instructions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(LensLibrary.class);
        LensLibrary aoc = new LensLibrary(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public LensLibrary(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = Arrays.stream(input.getFirst().split(","))
                .map(LensLibrary::parseInstruction)
                .toList();
        LOG.info("Found {} instructions...", instructions.size());
    }

    public long computeResultForPart1() {
        return instructions.stream()
                .mapToInt(Instruction::computeFullHash)
                .sum();
    }

    public long computeResultForPart2() {
        
        // Initialize the 256 boxes
        List<LensBox> boxes = IntStream.rangeClosed(0, 255)
                .mapToObj(LensBox::new)
                .toList();

        // Execute instructions
        instructions.forEach(
                instruction -> boxes.get(instruction.computeLabelHash()).execute(instruction)
        );
        
        // Compute focusing power
        return boxes.stream()
                .mapToInt(LensBox::computeFocusingPower)
                .sum();
    }

    public static Instruction parseInstruction(String input) {
        if (input.contains("-")) {
            return RemoveLens.parse(input);
        } else if (input.contains("=")) {
            return AddLens.parse(input);
        } else {
            throw new IllegalArgumentException("Unrecognized instruction: " + input);
        }
    }
}