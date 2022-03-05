package be.rouget.puzzles.adventofcode.year2021.day24;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArithmeticLogicUnit {

    private static final Logger LOG = LogManager.getLogger(ArithmeticLogicUnit.class);
    public static final int NUMBER_OF_MODULES = 14;
    private final List<Instruction> instructions;
    private final List<List<Instruction>> modules;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ArithmeticLogicUnit.class);
        ArithmeticLogicUnit aoc = new ArithmeticLogicUnit(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public ArithmeticLogicUnit(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = toInstructions(input);

        modules = Lists.partition(instructions, instructions.size() / NUMBER_OF_MODULES);
        for (int i = 0; i < NUMBER_OF_MODULES; i++) {
            List<Instruction> module = modules.get(i);
            LOG.info("Module[{}]: P0={}, P1={}, P2={}", i, module.get(4).getValue2(), module.get(5).getValue2(), module.get(15).getValue2());
        }
    }

    public static List<Instruction> toInstructions(List<String> input) {
        return input.stream()
                .map(Instruction::fromInput)
                .collect(Collectors.toList());
    }

    public String computeResultForPart1() {
        return findBestModelNumber(SearchMode.HIGHEST);
    }

    public String computeResultForPart2() {
        return findBestModelNumber(SearchMode.LOWEST);
    }

    private String findBestModelNumber(SearchMode searchMode) {

        // MONAD is a chain of 14 modules with each module reading a single digit input (between 1 and 9).
        // Each module only cares about the state of register Z and the input digit and computes a new value for Z.
        // Furthermore, every module is in fact the same code with only 3 varying parameters (P0, P1, P2).
        // P1 is either >= 10 or < 0
        // P0=1 when P1 >= 10 and P0=26 when P1 < 0
        // There are 7 modules with P1 >= 10 and 7 module with P1 <0.
        // Algorithm:
        // - if P1 >= 10 then Z' = Z*26  + INPUT + P2
        // - if P1 < 0 then Z' = Z/26 or Z' = Z + INPUT + P2 (depending on values of INPUT and Z and P1)
        // For the model number to be valid, we need that Z == 0 at the end.
        // This can only happen when Z' = Z/26 for all modules with P1 < 0, so we can constrain the search space to only
        // these cases.

        List<Integer> modelNumberDigits = findBestValidModelNumber(List.of(), 0, searchMode);
        if (modelNumberDigits == null) {
            throw new IllegalStateException("Did not find a valid model number for search mode " + searchMode);
        }
        String modelNumber = modelNumberDigits.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
        if (isValidModelNumber(modelNumber)) {
            return modelNumber;
        }
        throw new IllegalStateException("found model number is not valid: " + modelNumber);
    }

    private List<Integer> findBestValidModelNumber(List<Integer> currentDigits, int z, SearchMode searchMode) {
        if (currentDigits.size() == NUMBER_OF_MODULES) {
            // Model number is complete
            if (z == 0) {
                // Found valid model number
                return currentDigits;
            } else {
                // Model number is not valid
                return null;
            }
        }

        // Prepare module to run
        int moduleIndex = currentDigits.size();
        List<Instruction> currentModule = modules.get(moduleIndex);
        int p1 = Integer.parseInt(currentModule.get(5).getValue2());

        // Explore possible values for next digit in order which depends on the search mode
        List<Integer> nextDigits = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());
        if (searchMode == SearchMode.HIGHEST) {
            Collections.reverse(nextDigits);
        }
        for (int i : nextDigits) {
            int newZ = runModule(currentModule, z, i);
            boolean keepNewDigit = p1 >= 10 || newZ == z/26; // Constraint search space based on expected behaviour when p1 < 0
            if (keepNewDigit) {
                List<Integer> newDigits = Lists.newArrayList(currentDigits);
                newDigits.add(i);
                List<Integer> result = findBestValidModelNumber(newDigits, newZ, searchMode);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private int runModule(List<Instruction> currentModule, int z, int input) {
        ALUProcessingUnit unit = new ALUProcessingUnit(0, 0, 0, z);
        unit.executeProgram(currentModule, List.of(input));
        return unit.getVariable("z");
    }

    public boolean isValidModelNumber(String modelNumber) {
        if (!StringUtils.isNumeric(modelNumber) || modelNumber.length() != NUMBER_OF_MODULES || modelNumber.contains("0")) {
            throw new IllegalArgumentException("Illegal model number " + modelNumber);
        }
        List<Integer> input = AocStringUtils.extractCharacterList(modelNumber).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(instructions, input);
        return unit.getVariable("z") == 0;
    }

    public enum SearchMode {
        LOWEST, HIGHEST
    }
}