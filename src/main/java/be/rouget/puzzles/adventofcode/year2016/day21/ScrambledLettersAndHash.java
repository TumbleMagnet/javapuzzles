package be.rouget.puzzles.adventofcode.year2016.day21;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScrambledLettersAndHash {

    private static final Logger LOG = LogManager.getLogger(ScrambledLettersAndHash.class);
    private static final String INPUT1 = "abcdefgh";
    private static final String INPUT2 = "fbgdceah";

    private final List<ScramblingFunction> operations;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ScrambledLettersAndHash.class);
        ScrambledLettersAndHash aoc = new ScrambledLettersAndHash(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1(INPUT1));
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2(INPUT2));
    }

    public ScrambledLettersAndHash(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        operations = input.stream()
                .map(ScrambledLettersAndHash::parse)
                .collect(Collectors.toList());
    }

    public String computeResultForPart1(String input) {
        String output = input;
        for (ScramblingFunction operation : operations) {
            output = operation.scramble(output);
        }
        return output;
    }

    public String computeResultForPart2(String input) {
        List<ScramblingFunction> reversedOperations = Lists.newArrayList(operations);
        Collections.reverse(reversedOperations);
        String output = input;
        for (ScramblingFunction operation : reversedOperations) {
            output = operation.unScramble(output);
        }
        return output;
    }

    public static ScramblingFunction parse(String input) {
        List<Function<String, ScramblingFunction>> parseFunctions = Lists.newArrayList(
                SwapPosition::parse,
                SwapLetter::parse,
                Rotate::parse,
                RotateBasedOnLetter::parse,
                Reverse::parse,
                Move::parse
        );
        return parseFunctions.stream()
                .map(pf -> pf.apply(input))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot parse input \"" + input + "\" into function!"));
    }
}