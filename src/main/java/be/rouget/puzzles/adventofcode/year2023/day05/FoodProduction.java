package be.rouget.puzzles.adventofcode.year2023.day05;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static be.rouget.puzzles.adventofcode.util.AocStringUtils.extractParagraphs;


public class FoodProduction {

    private static final Logger LOG = LogManager.getLogger(FoodProduction.class);
    private final List<Long> seeds;
    private final List<Mapping> mappings;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(FoodProduction.class);
        FoodProduction aoc = new FoodProduction(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public FoodProduction(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        // Extract seeds
        seeds = parseSeeds(input.getFirst());
        LOG.info("Found {} seeds...", seeds.size());

        // Extract maps
        List<String> mapsInput = input.subList(2, input.size());
        LOG.info("Map input start: {}", mapsInput.getFirst());
        LOG.info("Map input end  : {}", mapsInput.getLast());

        List<List<String>> mapParagraphs = extractParagraphs(mapsInput);
        mappings = mapParagraphs.stream()
                .map(Mapping::parse)
                .toList();
        LOG.info("Found {} mappings...", mappings.size());
        mappings.forEach(m -> LOG.info("Mapping from {} to {} with {} lines...", m.from(), m.to(), m.lines().size()));
    }

    private static List<Long> parseSeeds(String input) {
        String[] tokens = input.replace("seeds: ", "").split(" ");
        return Arrays.stream(tokens).map(Long::parseLong).toList();
    }

    public long computeResultForPart1() {
        
        // Convert seeds to locations
        List<Long> currentNumbers = seeds;
        for (Mapping mapping : mappings) {
            currentNumbers = mapping.mapSourcesToDestinations(currentNumbers);
        }

        // Return minimum of locations
        return currentNumbers.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElseThrow();
    }

    public long computeResultForPart2() {
        
        // Convert seeds into ranges (startValue + length)
        List<LongRange> seedRanges = Lists.partition(seeds, 2).stream()
                .map(list -> new LongRange(list.getFirst(), list.getFirst() + list.getLast() - 1L))
                .toList();

        // Map the ranges through all mappings
        List<LongRange> currentRanges = seedRanges;
        for (Mapping mapping : mappings) {
            currentRanges = mapping.mapRanges(currentRanges);
        }

        // Return minimum of locations
        return currentRanges.stream()
                .mapToLong(LongRange::from)
                .min()
                .orElseThrow();
    }
}