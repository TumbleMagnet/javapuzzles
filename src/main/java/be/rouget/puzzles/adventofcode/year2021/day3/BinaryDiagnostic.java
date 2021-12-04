package be.rouget.puzzles.adventofcode.year2021.day3;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinaryDiagnostic {

    private static final String YEAR = "2021";
    private static final String DAY = "03";

    private static final Logger LOG = LogManager.getLogger(BinaryDiagnostic.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        BinaryDiagnostic aoc = new BinaryDiagnostic(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public BinaryDiagnostic(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        int size = input.get(0).length();
        String gamma = "";
        String epsilon = "";
        for (int i = 0; i < size; i++) {
            List<String> bytesForPosition = getBytesAtPosition(input, i);
            gamma += mostFrequentElement(bytesForPosition);
            epsilon += leastFrequentElement(bytesForPosition);
        }
        return parseBinary(gamma) * parseBinary(epsilon);
    }

    private List<String> getBytesAtPosition(List<String> values, int i) {
        List<String> bytesForPosition = Lists.newArrayList();
        for (String line : values) {
            bytesForPosition.add(AocStringUtils.splitCharacters(line)[i]);
        }
        return bytesForPosition;
    }

    public long computeResultForPart2() {
        String oxygenGeneratorRating = findRating(input, this::mostFrequentElement);
        String co2ScrubberRating = findRating(input, this::leastFrequentElement);
        return parseBinary(oxygenGeneratorRating) * parseBinary(co2ScrubberRating);
    }

    private long parseBinary(String binary) {
        return new BigInteger(binary, 2).longValue();
    }

    private String mostFrequentElement(List<String> elements) {
        return elements.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(new BitComparator())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private String leastFrequentElement(List<String> elements) {
        return elements.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .min(new BitComparator())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private String findRating(List<String> input, Function<List<String>, String> bitCriteria) {
        List<String> elements = input;
        int index = 0;
        while (elements.size() > 1) {
            elements = findRatingsForCriteria(elements, index, bitCriteria);
            index++;
        }
        return elements.get(0);
    }

    private List<String> findRatingsForCriteria(List<String> input, int index, Function<List<String>, String> bitCriteria) {
        List<String> bytesAtPosition = getBytesAtPosition(input, index);
        String filterBit = bitCriteria.apply(bytesAtPosition);
        return input.stream()
                .filter(line -> filterBit.equals(AocStringUtils.splitCharacters(line)[index]))
                .collect(Collectors.toList());
    }

    public static class BitComparator implements Comparator<Map.Entry<String, Long>>  {

        @Override
        public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
            int valueCompare = o1.getValue().compareTo(o2.getValue());
            if (valueCompare != 0) {
                return valueCompare;
            } else {
                return o1.getKey().compareTo(o2.getKey()); // Keep 1 when searching max, keep 0 when searching min
            }
        }
    }

}