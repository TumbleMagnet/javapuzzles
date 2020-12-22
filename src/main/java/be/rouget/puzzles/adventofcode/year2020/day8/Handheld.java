package be.rouget.puzzles.adventofcode.year2020.day8;

import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class Handheld {

    private static Logger LOG = LogManager.getLogger(Handheld.class);

    private int accumulator = 0;
    private int instructionIndex = 0;
    private String[] program;
    private Set<Integer> visitedInstructions;

    public void runProgram(List<String> instructions) throws InfiniteLoopDetected {
        this.program = instructions.toArray(new String[0]);
        this.accumulator = 0;
        this.visitedInstructions = Sets.newHashSet();
        while (executeNextInstruction()) {
            LOG.info("After instruction: accumulator={}, instructionIndex={}", accumulator, instructionIndex);
        }
    }

    private boolean executeNextInstruction() throws InfiniteLoopDetected {
        if (instructionIndex >= program.length) {
            LOG.info("Index {} is beyond the end of the program, halting...", instructionIndex);
            return false;
        }
        String line = program[instructionIndex];
        LOG.info("Read line [{}] at index {}...", line, instructionIndex);
        Instruction instruction = Instruction.parse(line);
        if (visitedInstructions.contains(instructionIndex)) {
            throw new InfiniteLoopDetected();
        }
        visitedInstructions.add(instructionIndex);
        LOG.info("Executing instruction {}", instruction);
        switch (instruction.getCode()) {
            case ACC:
                accumulator += instruction.getValue();
                instructionIndex++;
                return true;
            case JMP:
                instructionIndex += instruction.getValue();
                return true;
            case NOP:
                instructionIndex++;
                return true;
        }
        throw new IllegalStateException("Unsupported instruction " + instruction);
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    public int getAccumulator() {
        return accumulator;
    }
}
