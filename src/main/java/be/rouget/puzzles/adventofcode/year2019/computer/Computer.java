package be.rouget.puzzles.adventofcode.year2019.computer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.year2019.computer.InstructionOutcome.*;
import static java.lang.Math.toIntExact;

public class Computer {

    private static Logger LOG = LogManager.getLogger(Computer.class);
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    private static final int RELATIVE_MODE = 2;

    private long[] memory = null;
    private int instructionIndex = 0;
    private int relativeBase = 0;

    private long[] inputValues;
    private int inputIndex = 0;

    private List<String> output = Lists.newArrayList();

    public Computer(String program) {
        memory = Arrays.stream(program.split(",")).mapToLong(Long::valueOf).toArray();
        LOG.debug("Loaded " + memory.length + " positions into memory.");
    }

    private void setInputValues(long... values) {
        this.inputValues = values;
        this.inputIndex = 0;
    }

    public ComputerState run(long... newInputValues) {
        setInputValues(newInputValues);
        boolean shouldContinue = true;
        while (true) {
            InstructionOutcome outcome = runCurrentInstruction();
            if (outcome == HALT) {
                return ComputerState.HALTED;
            }
            if (outcome == WAIT_FOR_INPUT) {
                return ComputerState.WAITING_FOR_INPUT;
            }
        }
    }

    public List<String> getOutput() {
        List<String> outputToReturn = output;
        output = Lists.newArrayList();
        return outputToReturn;
    }

    public List<Integer> getOutputAsIntegers() {
        return getOutput().stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public List<Long> getOutputAsLongs() {
        return getOutput().stream().map(Long::valueOf).collect(Collectors.toList());
    }

    private InstructionOutcome runCurrentInstruction() {

        // Read next instruction
        long instructionValue = getMemoryAtPosition(instructionIndex);
        int opCode = extractOpcode(instructionValue);
        Instructions instruction = Instructions.fromOpCode(opCode);

        // Read parameters corresponding to instruction
        int[] parameterModes = extractParameterModes(instructionValue, instruction.getNumberOfParameters());
        Parameter[]  parameters = readInstructionParameters(parameterModes);
        int instructionIndexBeforeExecution = instructionIndex;

        // Execute instruction
        InstructionOutcome outcome = executeInstruction(instruction, parameters);
        if (CONTINUE == outcome) {
            // Increment instruction pointer for next instruction (only if not changed by instruction)
            if (instructionIndex == instructionIndexBeforeExecution) {
                instructionIndex += 1 + instruction.getNumberOfParameters();
            }
        }

        return outcome;
    }

    private Parameter[] readInstructionParameters(int[] parameterModes) {
        List<Parameter> parameters = Lists.newArrayList();
        for (int i=0; i<parameterModes.length; i++) {
            Parameter p = new Parameter(
                    parameterModes[i],
                    getMemoryAtPosition(instructionIndex + (i+1)));
            parameters.add(p);
        }
        return parameters.toArray(new Parameter[parameterModes.length]);
    }

    private int extractOpcode(long instructionValue) {
        String paddedValue = StringUtils.leftPad(String.valueOf(instructionValue), 2, '0');
        String opCodeString = StringUtils.right(paddedValue, 2);
        return Integer.valueOf(opCodeString);
    }

    private int[] extractParameterModes(long instructionValue, int numberOfParameters) {
        String paddedValue = StringUtils.leftPad(String.valueOf(instructionValue), 2+numberOfParameters, '0');
        String modesString = StringUtils.left(paddedValue, numberOfParameters);
        List<Integer> modes = Lists.newArrayList();
        for (char c: modesString.toCharArray()) {
            modes.add(Character.getNumericValue(c));
        }
        modes = Lists.reverse(modes); // Mode digits are from right to left
        return modes.stream().mapToInt(i -> i).toArray();
    }

    private InstructionOutcome executeInstruction(Instructions instruction, Parameter[] parameters) {
        LOG.debug("Executing " + instruction + " with parameters " + Arrays.toString(parameters));
        switch (instruction) {
            case ADD:           return executeAdd(parameters);
            case MULTIPLY:      return executeMultiply(parameters);
            case INPUT:         return executeInput(parameters);
            case OUTPUT:        return executeOutput(parameters);
            case HALT:          return executeHalt(parameters);
            case JUMP_IF_TRUE:  return executeJumpIfTrue(parameters);
            case JUMP_IF_FALSE: return executeJumpIfFalse(parameters);
            case LESS_THAN:     return executeLessThan(parameters);
            case EQUALS:        return executeEquals(parameters);
            case UPDATE_RELATIVE_BASE: return executeUpdateRelativeBase(parameters);
            default:
                throw new IllegalArgumentException("Unsupported instruction " + instruction);
        }

    }

    private InstructionOutcome executeAdd(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.ADD);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        long destination = readOutputParameter(parameters[2]);
        setMemoryAtPosition(destination, value1 + value2);
        return CONTINUE;
    }

    private InstructionOutcome executeMultiply(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.MULTIPLY);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        long destination = readOutputParameter(parameters[2]);
        setMemoryAtPosition(destination, value1 * value2);
        return CONTINUE;
    }

    private InstructionOutcome executeInput(Parameter[] parameters) {
        try {
            long input = nextInput();
            validateNumberOfParameters(parameters, Instructions.INPUT);
            long destination = readOutputParameter(parameters[0]);
            setMemoryAtPosition(destination, input);
            return CONTINUE;
        }
        catch (NoMoreInputException e) {
            return WAIT_FOR_INPUT;
        }
    }

    private InstructionOutcome executeOutput(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.OUTPUT);
        long value = readInputParameter(parameters[0]);
        writeOutput(value);
        return CONTINUE;
    }

    private InstructionOutcome executeJumpIfTrue(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.JUMP_IF_TRUE);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        if (value1 != 0) {
            instructionIndex = toIntExact(value2);
        }
        return CONTINUE;
    }

    private InstructionOutcome executeJumpIfFalse(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.JUMP_IF_FALSE);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        if (value1 == 0) {
            instructionIndex = toIntExact(value2);
        }
        return CONTINUE;
    }

    private InstructionOutcome executeLessThan(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.LESS_THAN);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        long destination = readOutputParameter(parameters[2]);
        setMemoryAtPosition(destination, value1 < value2 ? 1 : 0);
        return CONTINUE;
    }

    private InstructionOutcome executeEquals(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.EQUALS);
        long value1 = readInputParameter(parameters[0]);
        long value2 = readInputParameter(parameters[1]);
        long destination = readOutputParameter(parameters[2]);
        setMemoryAtPosition(destination, value1 == value2 ? 1 : 0);
        return CONTINUE;
    }

    private InstructionOutcome executeUpdateRelativeBase(Parameter[] parameters) {
        validateNumberOfParameters(parameters, Instructions.UPDATE_RELATIVE_BASE);
        long value1 = readInputParameter(parameters[0]);
        relativeBase += toIntExact(value1);
        return CONTINUE;
    }

    private InstructionOutcome executeHalt(Parameter[] parameters) {
        return HALT;
    }

    private long nextInput() throws NoMoreInputException {
        if (inputIndex>= inputValues.length) {
            throw new NoMoreInputException();
        }
        long indexParameter = inputIndex++;
        return inputValues[toIntExact(indexParameter)];
    }

    private void writeOutput(long value) {
        output.add(String.valueOf(value));
    }

    private void validateNumberOfParameters(Parameter[] parameters, Instructions instruction) {
        if (parameters.length != instruction.getNumberOfParameters()) {
            throw new IllegalArgumentException("Instruction " + instruction + " expects " + instruction.getNumberOfParameters() + " parameters");
        }
    }

    private long readInputParameter(Parameter parameter) {
        switch (parameter.getMode()) {
            case POSITION_MODE:  return getMemoryAtPosition(parameter.getValue());
            case IMMEDIATE_MODE: return parameter.getValue();
            case RELATIVE_MODE:  return getMemoryAtPosition(relativeBase + parameter.getValue());
            default: throw new IllegalArgumentException("Invalid mode " + parameter.getMode());
        }
    }

    private long readOutputParameter(Parameter parameter) {
        switch (parameter.getMode()) {
            case POSITION_MODE:  return parameter.getValue();
            case RELATIVE_MODE:  return relativeBase + parameter.getValue();
            default: throw new IllegalArgumentException("Invalid mode " + parameter.getMode());
        }
    }


    public long getMemoryAtPosition(long indexParameter) {
        int index = toIntExact(indexParameter);
        resizeMemoryIfNeeded(index);
        long value = memory[index];
        LOG.debug("Reading value at position " + index + ": " + value);
        return value;
    }

    public void setMemoryAtPosition(long indexParameter, long value) {
        int index = toIntExact(indexParameter);
        resizeMemoryIfNeeded(index);
        LOG.debug("Writing value at position " + index + ": " + value);
        memory[index] = value;
    }

    private void resizeMemoryIfNeeded(int requestedIndex) {
        if (requestedIndex >= memory.length) {
            LOG.debug("Out of bound memory index requested: " + requestedIndex + ", allocating memory up to " + requestedIndex*2);
            memory = Arrays.copyOf(memory, requestedIndex*2);
        }

    }

    public long[] dumpMemory() {
        return memory;
    }
}
