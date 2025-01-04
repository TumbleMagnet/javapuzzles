package be.rouget.puzzles.adventofcode.year2024.day01;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class HistorianHysteria {

    private static final Logger LOG = LogManager.getLogger(HistorianHysteria.class);
    private final List<PairOfLocations> pairs;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(HistorianHysteria.class);
        HistorianHysteria aoc = new HistorianHysteria(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public HistorianHysteria(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        pairs = input.stream()
                .map(PairOfLocations::parse)
                .toList();
    }

    public long computeResultForPart1() {

        // Build sorted list of left locations
        List<Long> leftLocations = pairs.stream()
                .map(PairOfLocations::left)
                .sorted()
                .toList();

        // Build sorted list of right locations
        List<Long> rightLocations = pairs.stream()
                .map(PairOfLocations::right)
                .sorted()
                .toList();

        // Build new pairs from the sorted lists
        return IntStream.range(0, leftLocations.size())
                .mapToObj(index -> new PairOfLocations(leftLocations.get(index), rightLocations.get(index)))
                .mapToLong(PairOfLocations::computeDistance)
                .sum();
    }

    public long computeResultForPart2() {

        // Build count of occurrences of location ids in the right list
        Map<Long, Long> countsInRightList = pairs.stream()
                .map(PairOfLocations::right)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Compute similarity score
        return pairs.stream()
                .map(PairOfLocations::left)
                .mapToLong(locationId -> locationId * countsInRightList.getOrDefault(locationId, 0L))
                .sum();
    }
}