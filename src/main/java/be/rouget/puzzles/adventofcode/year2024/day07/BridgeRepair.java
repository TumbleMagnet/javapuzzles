package be.rouget.puzzles.adventofcode.year2024.day07;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2024.day07.Operator.*;

public class BridgeRepair {

    private static final Logger LOG = LogManager.getLogger(BridgeRepair.class);
    private final List<CalibrationEquation> equations;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(BridgeRepair.class);
        BridgeRepair aoc = new BridgeRepair(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public BridgeRepair(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        equations = input.stream()
                .map(CalibrationEquation::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return sumOfValidEquations(List.of(ADD, MULTIPLY));
    }

    public long computeResultForPart2() {
        return sumOfValidEquations(List.of(ADD, MULTIPLY, CONCATENATE));
    }

    private long sumOfValidEquations(List<Operator> operators) {
        return equations.stream()
                .filter(equation -> isTrue(equation, operators))
                .mapToLong(CalibrationEquation::result)
                .sum();
    }

    private static boolean isTrue(CalibrationEquation equation, List<Operator> operators) {
        Long firstValue = equation.values().getFirst();
        List<Long> remainingValues = cloneAndRemoveFirstElement(equation.values());
        return isTrue(equation.result(), firstValue, remainingValues, operators);
    }

    private static boolean isTrue(long expectedResult, long currentValue, List<Long> remainingValues, List<Operator> operators) {
        if (remainingValues.isEmpty()) {
            // Compare final value to expected result
            return expectedResult == currentValue;
        }
        // Since there are no zero and negative values, if current value is already larger than result, it cannot be true
        if (currentValue > expectedResult) {
            return false;
        }

        // Validate recursively by trying different operators
        long nextValue = remainingValues.getFirst();
        List<Long> newRemainingValues = cloneAndRemoveFirstElement(remainingValues);
        for (Operator operator : operators) {
            long newCurrentValue = operator.evaluate(currentValue, nextValue);
            if (isTrue(expectedResult, newCurrentValue, newRemainingValues, operators)) {
                return true;
            }
        }

        // Could not find a permutation matching the expected result
        return false;
    }

    private static List<Long> cloneAndRemoveFirstElement(List<Long> original) {
        List<Long> result = Lists.newArrayList(original);
        result.removeFirst();
        return result;
    }
}