package be.rouget.puzzles.adventofcode.year2022.day10;

import java.util.List;
import java.util.Optional;

public class VideoCpu {
    
    private final List<Instruction> program;
    private int indexOfNextInstruction;
    private int registerX;
    private int executedCycles;
    private int executionCycleForCurrentInstruction;
    private Instruction currentInstruction;

    public VideoCpu(List<Instruction> program) {
        this.program = program;
        this.executedCycles = 0;
        this.indexOfNextInstruction = 0;
        this.registerX = 1;
        this.currentInstruction = null;
        this.executionCycleForCurrentInstruction = 0;
    }
    
    public boolean executeCycle() {

        boolean fetchNewInstruction = currentInstruction == null // Program is starting
                || executionCycleForCurrentInstruction >= currentInstruction.code().getNumberOfCycles(); // Current instruction is done
        if (fetchNewInstruction) {
            // Current instruction is done, fetch next instruction
            Optional<Instruction> optionalInstruction = fetchNextInstruction();
            if (optionalInstruction.isEmpty()) {
                // Program ended
                return false;
            }
            currentInstruction = optionalInstruction.get();
            executionCycleForCurrentInstruction = 0;
        }
        
        // Execute cycle for current instruction
        executeInstructionCycle();
        
        // Cycle is done
        executedCycles += 1;
        return true;
    }

    private void executeInstructionCycle() {
        executionCycleForCurrentInstruction += 1;

        // Do nothing unless cycle 2 of ADDX
        if (InstructionCode.ADDX == currentInstruction.code() && executionCycleForCurrentInstruction == 2) {
            registerX += currentInstruction.value();
        }
    }

    public int getRegisterX() {
        return registerX;
    }

    public int getExecutedCycles() {
        return executedCycles;
    }
    
    private Optional<Instruction> fetchNextInstruction() {
        if (indexOfNextInstruction >= program.size()) {
            return Optional.empty();
        }
        return Optional.of(program.get(indexOfNextInstruction++));
    }
    
}
