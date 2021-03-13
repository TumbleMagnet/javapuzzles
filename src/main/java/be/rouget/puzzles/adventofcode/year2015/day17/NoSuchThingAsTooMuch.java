package be.rouget.puzzles.adventofcode.year2015.day17;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoSuchThingAsTooMuch {

    private static final String YEAR = "2015";
    private static final String DAY = "17";

    private static final Logger LOG = LogManager.getLogger(NoSuchThingAsTooMuch.class);
    private static final int EGGNOG_VOLUME = 150;
    private final List<List<Integer>> combinations;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        NoSuchThingAsTooMuch aoc = new NoSuchThingAsTooMuch(EGGNOG_VOLUME, input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public NoSuchThingAsTooMuch(int eggnogVolume, List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        List<Integer> containers = input.stream().map(Integer::parseInt).collect(Collectors.toList());
        this.combinations = listPossibleCombinations(eggnogVolume, Lists.newArrayList(), containers);
    }

    public long computeResultForPart1() {
        return combinations.size();
    }

    public long computeResultForPart2() {
        Map<Integer, List<List<Integer>>> combinationsBySize = combinations.stream().collect(Collectors.groupingBy(List::size));
        int minimumSize = combinationsBySize.keySet().stream().mapToInt(Integer::intValue).min().orElseThrow();
        return combinationsBySize.get(minimumSize).size();
    }

    private List<List<Integer>> listPossibleCombinations(int volume, List<Integer> filledContainers, List<Integer> emptyContainers) {
        if (emptyContainers.isEmpty()) {
            return Lists.newArrayList();
        }

        List<List<Integer>> combinations = Lists.newArrayList();

        // Pick next container
        Integer container = emptyContainers.get(0);
        List<Integer> remainingContainers = copyAndRemove(emptyContainers, container);

        // Count combinations using that container (if possible)
        if (container == volume) {
            // Filling the container leads to an additional combination
            combinations.add(copyAndAdd(filledContainers, container));
        } else if (container < volume) {
            // Fill the container and continue recursively
            int remainingVolume = volume - container;
            combinations.addAll(listPossibleCombinations(remainingVolume, copyAndAdd(filledContainers, container), remainingContainers));
        }

        // Count combinations not using that container
        combinations.addAll(listPossibleCombinations(volume, filledContainers, remainingContainers));

        // Return sum
        return combinations;
    }

    private List<Integer> copyAndAdd(List<Integer> availableContainers, Integer container) {
        List<Integer> result = Lists.newArrayList(availableContainers);
        result.add(container);
        return result;
    }

    private List<Integer> copyAndRemove(List<Integer> availableContainers, Integer container) {
        List<Integer> filtered = Lists.newArrayList(availableContainers);
        filtered.remove(container);
        return filtered;
    }

}