package be.rouget.puzzles.adventofcode.year2021.day24;

import com.google.common.collect.Lists;

import java.util.List;

public class ALUProcessingUnit {
    private int w;
    private int x;
    private int y;
    private int z;

    private List<Integer> input;
    private int inputIndex = 0;

    public ALUProcessingUnit() {
        this(0, 0, 0, 0);
    }

    public ALUProcessingUnit(int w, int x, int y, int z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void executeProgram(List<Instruction> instructions, List<Integer> input) {
        this.inputIndex = 0;
        this.input = Lists.newArrayList(input);
        instructions.forEach(this::executeInstruction);
    }

    public void executeInstruction(Instruction instruction) {
        switch (instruction.getCode()) {
            case INPUT: executeInput(instruction.getValue1()); break;
            case ADD: executeAdd(instruction.getValue1(), instruction.getValue2()); break;
            case MULTIPLY: executeMultiply(instruction.getValue1(), instruction.getValue2()); break;
            case DIVIDE: executeDivide(instruction.getValue1(), instruction.getValue2()); break;
            case MODULO: executeModulo(instruction.getValue1(), instruction.getValue2()); break;
            case EQUALS: executeEquals(instruction.getValue1(), instruction.getValue2()); break;
            default:
                throw new IllegalArgumentException("Unsupported instruction " + instruction);
        }
    }

    private void executeInput(String value1) {
        setVariable(value1, readNextInput());
    }

    private void executeAdd(String value1, String value2) {
        int result = getVariable(value1) + getVariableOrValue(value2);
        setVariable(value1, result);
    }

    private void executeMultiply(String value1, String value2) {
        int result = getVariable(value1) * getVariableOrValue(value2);
        setVariable(value1, result);
    }

    private void executeDivide(String value1, String value2) {
        int result = getVariable(value1) / getVariableOrValue(value2);
        setVariable(value1, result);
    }

    private void executeModulo(String value1, String value2) {
        int result = getVariable(value1) % getVariableOrValue(value2);
        setVariable(value1, result);
    }

    private void executeEquals(String value1, String value2) {
        int result = getVariable(value1) == getVariableOrValue(value2) ? 1 :0;
        setVariable(value1, result);
    }

    private int getVariableOrValue(String value2) {
        if ("w".equals(value2)
                || "x".equals(value2)
                || "y".equals(value2)
                || "z".equals(value2)
        ) {
            return getVariable(value2);
        }
        return Integer.parseInt(value2);
    }

    public int getVariable(String name) {
        switch (name) {
            case "w": return w;
            case "x": return x;
            case "y": return y;
            case "z": return z;
            default:
                throw new IllegalArgumentException("Invalid variable name: " + name);
        }
    }

    private void setVariable(String name, int value) {
        switch (name) {
            case "w": w = value; break;
            case "x": x = value; break;
            case "y": y = value; break;
            case "z": z = value; break;
            default:
                throw new IllegalArgumentException("Invalid variable name: " + name);
        }
    }

    private int readNextInput() {
        if (inputIndex >= input.size()) {
            throw new IllegalStateException("Cannot read input at index " + inputIndex);
        }
        int value = input.get(inputIndex);
        inputIndex++;
        return value;
    }
}
