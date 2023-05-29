package be.rouget.puzzles.adventofcode.year2022.day20;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class GrovePositioningSystem {

    private static final Logger LOG = LogManager.getLogger(GrovePositioningSystem.class);
    private static final long DECRYPTION_KEY = 811589153L;
    private final List<Long> encryptedInput;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(GrovePositioningSystem.class);
        GrovePositioningSystem aoc = new GrovePositioningSystem(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public GrovePositioningSystem(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        encryptedInput = input.stream()
                .map(Long::parseLong)
                .toList();
    }

    public long computeResultForPart1() {
        List<Long> mixedInput = mix(encryptedInput);
        return sumOfGrooveCoordinates(mixedInput);
    }

    public static List<Long> mix(List<Long> input) {

        // Convert input into a list of values with their original indexes
        List<IndexedValue> indexedValues = IntStream.range(0, input.size())
                .mapToObj(i -> new IndexedValue(input.get(i), i))
                .collect(Collectors.toCollection(ArrayList::new));

        // Mix every value in their original order
        for (int i = 0; i < input.size(); i++) {
            mixValueWithIndex(indexedValues, i);
        }

        // Extract result
        return indexedValues.stream()
                .map(IndexedValue::value)
                .toList();
    }

    public long computeResultForPart2() {
        List<Long> mixedInput = mixPart2(encryptedInput);
        return sumOfGrooveCoordinates(mixedInput);
    }

    public static List<Long> mixPart2(List<Long> input) {

        // Convert input into a list of values (multiplied by the decryption key) with their original indexes
        List<IndexedValue> indexedValues = IntStream.range(0, input.size())
                .mapToObj(i -> new IndexedValue(input.get(i) * DECRYPTION_KEY, i))
                .collect(Collectors.toCollection(ArrayList::new));

        // Mix 10 times
        for (int mixIndex = 0; mixIndex < 10; mixIndex++) {
            // Mix every value in their original order
            for (int i = 0; i < input.size(); i++) {
                mixValueWithIndex(indexedValues, i);
            }
        }

        // Extract result
        return indexedValues.stream()
                .map(IndexedValue::value)
                .toList();
    }

    private static long sumOfGrooveCoordinates(List<Long> mixedInput) {
        int indexOfZero = mixedInput.indexOf(0L);
        int inputSize = mixedInput.size();
        LOG.info("Length of input is: {}", inputSize);
        LOG.info("Index of zero is: {}", indexOfZero);
        long value1 = mixedInput.get((indexOfZero + 1000) % inputSize);
        long value2 = mixedInput.get((indexOfZero + 2000) % inputSize);
        long value3 = mixedInput.get((indexOfZero + 3000) % inputSize);

        return value1 + value2 + value3;
    }

    public static void mixValueWithIndex(List<IndexedValue> input, int originalIndex) {

        // Find current index and value of target element with specified original index
        int currentIndex = -1;
        IndexedValue valueToMix = null;
        for (int i = 0; i < input.size(); i++) {
            IndexedValue indexedValue = input.get(i);
            if (indexedValue.index() == originalIndex) {
                currentIndex = i;
                valueToMix = indexedValue;
                break;
            }
        }
        if (valueToMix == null || currentIndex < 0) {
            throw new IllegalStateException("Did not find value with original index " + originalIndex);
        }
        
        // Remove existing value
        input.remove(currentIndex);

        // Add value again at right position
        long shortSize = input.size();
        int newIndex = currentIndex + Math.toIntExact(valueToMix.value() % shortSize);
        if (newIndex < 0) {
            newIndex += shortSize;
        }
        else if (newIndex > shortSize) {
            newIndex -= shortSize;
        }
        input.add(newIndex, valueToMix);
    }
}