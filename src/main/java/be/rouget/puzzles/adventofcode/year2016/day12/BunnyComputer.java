package be.rouget.puzzles.adventofcode.year2016.day12;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BunnyComputer {

    private static final Logger LOG = LogManager.getLogger(BunnyComputer.class);
    private final List<Integer> registers;
    private final List<Instruction> instructions;
    private int nextInstruction = 0;

    public BunnyComputer(List<Instruction> instructions) {
        this(instructions, 0, 0, 0, 0);
    }

    public BunnyComputer(List<Instruction> instructions, Integer... registers) {
        this.instructions = instructions;
        if (registers.length != 4) {
            throw new IllegalArgumentException("Please provide 4 register values!");
        }
        this.registers = Lists.newArrayList(registers);
    }

    public void run() {
        while (nextInstruction < instructions.size()) {
            executeInstruction(instructions.get(nextInstruction));
        }
    }

    public int getRegisterValue(String registerName) {
        int registerIndex = getRegisterIndex(registerName);
        return registers.get(registerIndex);
    }

    public void setRegisterValue(int value, String registerName) {
        int registerIndex = getRegisterIndex(registerName);
        registers.set(registerIndex, value);
    }

    private int getRegisterIndex(String registerName) {
        switch (registerName) {
            case "a": return 0;
            case "b": return 1;
            case "c": return 2;
            case "d": return 3;
            default:
                throw new IllegalArgumentException("Unknown register name: " + registerName);
        }
    }

    private void executeInstruction(Instruction instruction) {
        LOG.debug("Executing instruction({}): {}", nextInstruction, instruction);
        switch (instruction.getCode()) {
            case CPY: executeCopy(instruction); break;
            case DEC: executeDecrement(instruction); break;
            case INC: executeIncrement(instruction); break;
            case JNZ: executeJumpIfNotZero(instruction); break;
        }
    }

    private void executeCopy(Instruction instruction) {
        int value = valueOrRegister(instruction.getArguments().get(0));
        String targetRegister = instruction.getArguments().get(1);
        setRegisterValue(value, targetRegister);
        nextInstruction++;
    }

    private void executeIncrement(Instruction instruction) {
        String targetRegister = instruction.getArguments().get(0);
        setRegisterValue(getRegisterValue(targetRegister) + 1, targetRegister);
        nextInstruction++;
    }

    private void executeDecrement(Instruction instruction) {
        String targetRegister = instruction.getArguments().get(0);
        setRegisterValue(getRegisterValue(targetRegister) -1, targetRegister);
        nextInstruction++;
    }

    private void executeJumpIfNotZero(Instruction instruction) {
        int testValue = valueOrRegister(instruction.getArguments().get(0));
        int jumpValue = Integer.parseInt(instruction.getArguments().get(1));
        if (testValue != 0) {
            nextInstruction += jumpValue;
        } else {
            nextInstruction++;
        }
    }

    private int valueOrRegister(String argument) {
        return StringUtils.isNumeric(argument) ? Integer.parseInt(argument) : getRegisterValue(argument);
    }
}
