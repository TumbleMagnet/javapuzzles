package be.rouget.puzzles.adventofcode.year2015.day23;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Computer {
    private static final Logger LOG = LogManager.getLogger(Computer.class);

    private final Instruction[] instructions;
    private final Map<Register, Integer> registers;

    private int nextInstruction = 0;

    public Computer(List<Instruction> instructionList) {
        this.instructions = instructionList.toArray(new Instruction[] {});
        this.registers = Maps.newHashMap();
        for (Register register : Register.values()) {
            registers.put(register, 0);
        }
        nextInstruction = 0;
    }

    public void execute() {
        logState();
        Instruction instruction = getNextInstruction();
        while (instruction != null) {
            executeInstruction(instruction);
            instruction = getNextInstruction();
        }
    }

    private Instruction getNextInstruction() {
        if (nextInstruction < 0 || nextInstruction >= instructions.length) {
            return null;
        }
        return instructions[nextInstruction];
    }

    private void executeInstruction(Instruction instruction) {
        LOG.debug("Executing instruction {}", instruction);
        switch (instruction.getCode()) {
            case HLF: executeHalf(instruction); break;
            case TPL: executeTriple(instruction); break;
            case INC: executeIncrement(instruction); break;
            case JMP: executeJump(instruction); break;
            case JIE: executeJumpIfEven(instruction); break;
            case JIO: executeJumpIfOne(instruction); break;
        }
        logState();
    }

    private void logState() {
        LOG.debug("Registers - A: {}, B: {}", registers.get(Register.A), registers.get(Register.B));
        LOG.debug("Next instruction {}", nextInstruction);
    }

    private void executeHalf(Instruction instruction) {
        Integer registerValue = registers.get(instruction.getRegister());
        registers.put(instruction.getRegister(), registerValue / 2);
        nextInstruction += 1;
    }

    private void executeTriple(Instruction instruction) {
        Integer registerValue = registers.get(instruction.getRegister());
        registers.put(instruction.getRegister(), registerValue * 3);
        nextInstruction += 1;
    }

    private void executeIncrement(Instruction instruction) {
        Integer registerValue = registers.get(instruction.getRegister());
        registers.put(instruction.getRegister(), registerValue + 1);
        nextInstruction += 1;
    }

    private void executeJump(Instruction instruction) {
        nextInstruction = nextInstruction + instruction.getOffset();
    }

    private void executeJumpIfOne(Instruction instruction) {
        Integer registerValue = registers.get(instruction.getRegister());
        if (registerValue == 1) {
            nextInstruction = nextInstruction + instruction.getOffset();
        } else {
            nextInstruction += 1;
        }
    }

    private void executeJumpIfEven(Instruction instruction) {
        Integer registerValue = registers.get(instruction.getRegister());
        if (registerValue % 2 == 0) {
            nextInstruction = nextInstruction + instruction.getOffset();
        } else {
            nextInstruction += 1;
        }
    }

    public int getRegister(Register register) {
        return registers.get(register);
    }
    public void setRegister(Register register, int value) {
        registers.put(register, value);
    }
}
