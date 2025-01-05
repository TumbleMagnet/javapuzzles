package be.rouget.puzzles.adventofcode.year2024.day03;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MullItOver {

    private static final Logger LOG = LogManager.getLogger(MullItOver.class);
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(MullItOver.class);
        MullItOver aoc = new MullItOver(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public MullItOver(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return input.stream()
                .mapToLong(MullItOver::executeLine)
                .sum();
    }

    public long computeResultForPart2() {
        // Concatenate input lines (as do/dont instructions continue to be valid)
        String concatenatedInput = String.join("", input);
        return executeLineWithConditions(concatenatedInput);
    }

    public static long executeLine(String inputLine) {

        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(inputLine);
        List<String> operations = Lists.newArrayList();
        while (matcher.find()) {
            String operation = matcher.group(0);
            operations.add(operation);
        }

        // Evaluate operations and return the sum
        return operations.stream()
                .mapToLong(MullItOver::evaluateOperation)
                .sum();
    }

    public static long executeLineWithConditions(String inputLine) {

        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(inputLine);
        List<String> instructions = Lists.newArrayList();
        while (matcher.find()) {
            String operation = matcher.group(0);
            instructions.add(operation);
        }

        // Evaluate instructions
        long result = 0L;
        boolean areOperationsEnabled = true;
        for (String instruction : instructions) {
            if ("do()".equals(instruction)) {
                areOperationsEnabled = true;
            } else if ("don't()".equals(instruction)) {
                areOperationsEnabled = false;
            } else {
                if (areOperationsEnabled) {
                    result += evaluateOperation(instruction);
                }
            }
        }
        return result;
    }

    public static long evaluateOperation(String operation) {
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(operation);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse operation: " + operation);
        }
        long left = Long.parseLong(matcher.group(1));
        long right = Long.parseLong(matcher.group(2));
        return left * right;
    }
}