package be.rouget.puzzles.adventofcode.year2020.day10;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdapterArray {

    private static final String YEAR = "2020";
    private static final String DAY = "10";

    private static final Logger LOG = LogManager.getLogger(AdapterArray.class);

    private final List<String> input;

    public AdapterArray(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AdapterArray aoc = new AdapterArray(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        List<Integer> sortedValues = input.stream()
                .map(Integer::parseInt)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        int previousValue = 0;
        int countOfOnes = 0;
        int countOfThrees = 0;
        for (Integer currentValue : sortedValues) {
            int diff = currentValue - previousValue;
            if (diff == 1) {
                countOfOnes++;
            } else if (diff == 3) {
                countOfThrees++;
            } else {
                throw new IllegalStateException("Difference between " + previousValue + " and " + currentValue + " is " + diff);
            }
            previousValue = currentValue;
        }
        // Last difference to device is always 3
        countOfThrees++;

        return (long) countOfOnes * countOfThrees;
    }
    public long computeResultForPart2() {
        List<Integer> reversedValues = input.stream()
                .map(Integer::parseInt)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        reversedValues.add(0);
        Map<Integer, Long> numberOfPathsPerAdapter = Maps.newHashMap();
        Integer lastValue = null;
        for (Integer currentValue : reversedValues) {
            if (numberOfPathsPerAdapter.isEmpty()) {
                numberOfPathsPerAdapter.put(currentValue, 1L);
            }
            else {
                // Number of path for current value is the sum of path from higher values reachable from current value
                long sum = 0;
                for (int delta = 1; delta<= 3; delta++) {
                    Long previousSum = numberOfPathsPerAdapter.get(currentValue + delta);
                    if (previousSum != null) {
                        sum += previousSum;
                    }
                }
                numberOfPathsPerAdapter.put(currentValue, sum);
            }
            lastValue = currentValue;
        }
        return numberOfPathsPerAdapter.get(lastValue);
    }
}