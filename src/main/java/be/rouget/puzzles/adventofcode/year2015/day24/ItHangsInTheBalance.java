package be.rouget.puzzles.adventofcode.year2015.day24;

import be.rouget.puzzles.adventofcode.util.SubListGenerator;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ItHangsInTheBalance {

    private static final String YEAR = "2015";
    private static final String DAY = "24";

    private static final Logger LOG = LogManager.getLogger(ItHangsInTheBalance.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ItHangsInTheBalance aoc = new ItHangsInTheBalance(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    private final List<Integer> packages;

    public ItHangsInTheBalance(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.packages = input.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return findBestSplit(3);
    }

    public long computeResultForPart2() {
        return findBestSplit(4);
    }

    private long findBestSplit(int numberOfGroups) {

        // Find the best way to split packages into three groups such that:
        // - the three groups have exactly the same weight
        // - group 1 has as few packages as possible
        // - group 1 has the smallest quantum entanglement (product of the weights)

        // Compute target weight for each group
        int totalWeight = this.packages.stream().mapToInt(Integer::valueOf).sum();
        if (totalWeight % numberOfGroups != 0) {
            throw new IllegalStateException("Total weight cannot be split into " + numberOfGroups + ": " + totalWeight);
        }
        int targetGroupWeight = totalWeight / numberOfGroups;
        LOG.info("Target weight per group: {}", targetGroupWeight);

        // Find smallest combination of weights which reaches target weight
        return computeBestSubset(this.packages, targetGroupWeight, numberOfGroups);
    }

    private Long computeBestSubset(List<Integer> elements, int targetGroupSum, int numberOfGroups) {
        return searchSubSet(elements, targetGroupSum, numberOfGroups, SearchMode.FIND_BEST);
    }

    private boolean canBeSplit(List<Integer> elements, int targetGroupSum, int numberOfGroups) {
        return searchSubSet(elements, targetGroupSum, numberOfGroups, SearchMode.FIND_FIRST) != null;
    }

    private Long searchSubSet(List<Integer> elements, int targetGroupSum, int numberOfGroups, SearchMode searchMode) {

        if (numberOfGroups == 1) {
            int sum = elements.stream().mapToInt(Integer::intValue).sum();
            if (sum == targetGroupSum) {
                return computeEntanglement(elements);
            }
            return null;
        }

        LOG.debug("Searching ({}) for sub-group of target sum {} of {} elements split into {} sub-groups...", searchMode, targetGroupSum, elements.size(), numberOfGroups);

        for (int size = 1; size <= elements.size(); size++) {

            LOG.debug("Checking for combinations of {} packages...", size);

            List<List<Integer>> subsets = SubListGenerator.subListsOfSize(elements, size);
            LOG.debug("{} possible combinations...", subsets.size());
            Long bestEntanglement = null;
            for (List<Integer> subset : subsets) {
                int sum = subset.stream().mapToInt(Integer::intValue).sum();
                if (sum != targetGroupSum) {
                    continue;
                }

                // If candidate subset does not have the best entanglement so far, no need to validate it further
                long candidateEntanglement = computeEntanglement(subset);
                if (bestEntanglement != null && candidateEntanglement >= bestEntanglement) {
                    continue;
                }

                // Validate that rest of packages can be split into n-1 groups of target weight
                LOG.debug("Found candidate of size {}, checking remaining {} groups...", subset.size(), numberOfGroups-1);
                List<Integer> remainingPackages = Lists.newArrayList(elements);
                remainingPackages.removeAll(subset);
                if (!canBeSplit(remainingPackages, targetGroupSum, numberOfGroups - 1)) {
                    LOG.info(" Could not find remaining sub-groups for candidate...");
                    continue;
                }
                LOG.debug("Candidate is valid...");

                if (searchMode == SearchMode.FIND_FIRST) {
                    return candidateEntanglement;
                }

                // Record as best candidate for this size
                bestEntanglement = candidateEntanglement;
            }

            if (bestEntanglement != null) {
                return bestEntanglement;
            }
        }

        // No solution
        return null;
    }

    private static long computeEntanglement(List<Integer> subset) {
        long entanglement = 1L;
        for (Integer weight : subset) {
            entanglement = entanglement * weight.longValue();
        }
        return entanglement;
    }

    public enum SearchMode {
        FIND_FIRST, FIND_BEST
    }
}