package be.rouget.puzzles.adventofcode.year2022.day03;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;


public class RucksackReorganization {

    private static final Logger LOG = LogManager.getLogger(RucksackReorganization.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(RucksackReorganization.class);
        RucksackReorganization aoc = new RucksackReorganization(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RucksackReorganization(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return input.stream()
                .map(this::findCommonItemInCompartments)
                .mapToInt(RucksackReorganization::itemTypeToPriority)
                .sum();
    }

    private Character findCommonItemInCompartments(String backpackItems) {
        // Split items into two compartments
        final int mid = backpackItems.length() / 2;
        String compartment1 = backpackItems.substring(0, mid);
        String compartment2 = backpackItems.substring(mid);
        if (compartment1.length() != compartment2.length()) {
            throw new IllegalArgumentException("Input string could not be split in equal halves: " + backpackItems);
        }
        // Find common item in the two compartments
        return findSingleCommonItem(List.of(compartment1, compartment2));
    }

    private Character findSingleCommonItem(List<String> listOfBackpackItems) {
        // Find common items
        Set<String> itemsForFirstElf = toUniqueItems(listOfBackpackItems.get(0));
        Set<String> commonElements = listOfBackpackItems.subList(1, listOfBackpackItems.size()).stream()
                .map(this::toUniqueItems)
                .reduce(itemsForFirstElf, Sets::intersection);

        // Extract single item
        if (commonElements.size() != 1) {
            throw new IllegalArgumentException("Input set is not composed of a single common element for set " + String.join(",", commonElements));
        }
        String elementAsString = commonElements.iterator().next();
        return elementAsString.charAt(0);
    }

    private Set<String> toUniqueItems(String itemsAsString) {
        return Sets.newHashSet(AocStringUtils.extractCharacterList(itemsAsString));
    }

    public static int itemTypeToPriority(Character input) {
        int offset = Character.isLowerCase(input) ? 0 : 26;
        char lowerCaseInput = Character.toLowerCase(input);
        return Character.getNumericValue(lowerCaseInput) - Character.getNumericValue('a') + 1 + offset;
    }

    public long computeResultForPart2() {
        return Lists.partition(input, 3).stream()
                .map(this::findSingleCommonItem)
                .mapToInt(RucksackReorganization::itemTypeToPriority)
                .sum();
    }
}