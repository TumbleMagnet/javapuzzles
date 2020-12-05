package be.rouget.puzzles.adventofcode.year2020;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AoC2020Day01 {

    private static Logger LOG = LogManager.getLogger(AoC2020Day01.class);

    private List<String> input;

    public AoC2020Day01(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2020_day01_input.txt");
        LOG.info("Input has {} lines", input.size());
        AoC2020Day01 aoc = new AoC2020Day01(input);

        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Pair selectedValues = findComplementsForTotal(toSortedValues(input), 2020L);
        if (selectedValues != null) {
            LOG.info("Found values after {} milliseconds", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return selectedValues.getProduct();
        }
        throw new IllegalStateException("Found no matching values");
    }

    private Pair findComplementsForTotal(List<Long> inputValues, long total) {
        Map<Long, Long> valueMap = inputValues.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
        for (Long candidate: inputValues) {
            Long complement = total - candidate;
            if (valueMap.containsKey(complement)) {
                return new Pair(candidate, complement);
            }
        }
        return null;
    }

    public long computeResultForPart2() {

        Stopwatch stopwatch = Stopwatch.createStarted();

        List<Long> allValues = toSortedValues(input);
        for (Long candidate1 : allValues) {

            // Find pair in remaning values whose sum is 2020 - candidate1
            List<Long> remaining = otherValues(allValues, candidate1);
            long subTotal = 2020L - candidate1;

            Pair pair = findComplementsForTotal(remaining, subTotal);
            if (pair != null) {
                LOG.info("Found values after {} milliseconds", stopwatch.elapsed(TimeUnit.MILLISECONDS));
                return pair.getProduct() * candidate1;
            }
        }
        throw new IllegalStateException("Found no matching values");
    }

    public static class Pair {

        private Long first;
        private Long second;
        public Pair(Long first, Long second) {
            this.first = first;
            this.second = second;
        }

        public Long getProduct() {
            return first * second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    private static List<Long> toSortedValues(List<String> input) {
        return input.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    private static List<Long> otherValues(List<Long> input, Long value) {
        return input.stream().filter(v -> !v.equals(value)).collect(Collectors.toList());
    }
}