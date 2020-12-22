package be.rouget.puzzles.adventofcode.year2020.day9;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class EncodingError {

    private static final String YEAR = "2020";
    private static final String DAY = "09";

    private static final Logger LOG = LogManager.getLogger(EncodingError.class);

    private final List<String> input;
    private final int preambleSize;

    public EncodingError(List<String> input, int preambleSize) {
        this.input = input;
        this.preambleSize = preambleSize;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_" + YEAR + "_day" + DAY + "_input.txt");
        EncodingError aoc = new EncodingError(input, 25);
        long result1 = aoc.computeResultForPart1();
        LOG.info("Result for part 1 is: " + result1);
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2(result1));
    }

    public long computeResultForPart1() {

        Long[] values = input.stream()
                .map(Long::parseLong).toArray(Long[]::new);
        for (int i = preambleSize; i < values.length; i++) {
            Long currentValue = values[i];
            Long[] longs = Arrays.copyOfRange(values, i - preambleSize, i);
            if (!containsSum(longs, currentValue)) {
                return currentValue;
            }
        }
        return -1;
    }

    private boolean containsSum(Long[] values, long sum) {
        for (int i = 0; i < values.length - 1; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] + values[j] == sum) {
                    return true;
                }
            }
        }
        return false;
    }

    public long computeResultForPart2(long expectedSum) {
        Long[] values = input.stream()
                .map(Long::parseLong).toArray(Long[]::new);
        for (int start = 0; start < values.length - 1; start++) {
            long sum = values[start];
            long min = values[start];
            long max = values[start];
            for (int end = start + 1; end < values.length; end++) {
                Long newValue = values[end];
                sum += newValue;
                min = Math.min(min, newValue);
                max = Math.max(max, newValue);
                if (sum == expectedSum) {
                    return min + max;
                }
                if (sum > expectedSum) {
                    break;
                }
            }
        }
        return -1;
    }
}