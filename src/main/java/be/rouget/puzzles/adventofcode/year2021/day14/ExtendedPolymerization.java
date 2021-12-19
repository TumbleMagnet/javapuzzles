package be.rouget.puzzles.adventofcode.year2021.day14;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExtendedPolymerization {

    private static final String YEAR = "2021";
    private static final String DAY = "14";

    private static final Logger LOG = LogManager.getLogger(ExtendedPolymerization.class);
    private final String template;
    private final Map<String, PairInsertionRule> rulesByPair;

    private final Map<InputKey, Map<String, Long>> cachedResults = Maps.newHashMap();

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ExtendedPolymerization aoc = new ExtendedPolymerization(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public ExtendedPolymerization(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        template = input.get(0);
        LOG.info("Template is {} ...", template);
        List<PairInsertionRule> rules = Lists.newArrayList();
        for (int i = 2; i < input.size(); i++) {
            rules.add(PairInsertionRule.parseRule(input.get(i)));
        }
        LOG.info("There are {} rules...", rules.size());
        rulesByPair = rules.stream()
                .collect(Collectors.toMap(PairInsertionRule::getPair, Function.identity()));
    }

    public long computeResultForPart1() {
        return processStepsRecursive(template, 10);
    }

    public long computeResultForPart2() {
        return processStepsRecursive(template, 40);
    }

    public long processStepsRecursive(String input, int numberOfSteps) {
        Map<String, Long> occurrences = executeStepsAndCountOccurrences(input, numberOfSteps);
        return computeResult(occurrences);
    }

    protected Map<String, Long> executeStepsAndCountOccurrences(String input, int numberOfSteps) {
        Map<String, Long> occurrences = countOccurrences(input);
        List<String> pairs = extractPairs(input);
        for (String pair : pairs) {
            merge(occurrences, countNewElementOccurrencesForPair(pair, numberOfSteps));
        }
        return occurrences;
    }

    private long computeResult(Map<String, Long> elementCount) {
        List<Map.Entry<String, Long>> sortedElements = elementCount.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue))
                .collect(Collectors.toList());
        Map.Entry<String, Long> mostFrequentEntry = sortedElements.get(sortedElements.size() - 1);
        LOG.info("Most frequent element is {} with {} occurrences...", mostFrequentEntry.getKey(), mostFrequentEntry.getValue());
        Map.Entry<String, Long> leastFrequentEntry = sortedElements.get(0);
        LOG.info("Least frequent element is {} with {} occurrences...", leastFrequentEntry.getKey(), leastFrequentEntry.getValue());
        return mostFrequentEntry.getValue() - leastFrequentEntry.getValue();
    }

    protected Map<String, Long> countOccurrences(String current) {
        return AocStringUtils.extractCharacterList(current).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<String, Long> countNewElementOccurrencesForPair(String pair, int numberOfSteps) {

        if (pair.length() != 2) {
            throw new IllegalArgumentException("Invalid pair: " + pair);
        }

        InputKey inputKey = new InputKey(pair, numberOfSteps);
        Map<String, Long> previousResult = cachedResults.get(inputKey);
        if (previousResult != null) {
            return previousResult;
        }

        // Check if pair can be extended
        PairInsertionRule pairInsertionRule = rulesByPair.get(pair);
        if (pairInsertionRule == null) {
            // This pair will not grow, return 0 new element
            Map<String, Long> result = Maps.newHashMap();
            cachedResults.put(inputKey, result);
            return result;
        }

        // Extend pair
        String extendedPair = pairInsertionRule.getResult();
        Map<String, Long> result = Maps.newHashMap();
        result.put(pairInsertionRule.getAddedElement(), 1L); // Count inserted character
        if (numberOfSteps == 1) {
            // No more steps, return counts after extension
            cachedResults.put(inputKey, result);
            return result;
        }

        // Additional steps
        List<String> newPairs = extractPairs(extendedPair);
        for (String newPair : newPairs) {
            Map<String, Long> newOccurrences = countNewElementOccurrencesForPair(newPair, numberOfSteps - 1);
            merge(result, newOccurrences);
        }
        cachedResults.put(inputKey, result);
        return result;
    }

    @VisibleForTesting
    protected List<String> extractPairs(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException("Invalid inpot for extractPairs(): " + input);
        }
        if (input.length() == 2) {
            return List.of(input);
        }
        List<String> pairs = Lists.newArrayList();
        for (int i = 0; i < input.length()-1; i++) {
            pairs.add(input.substring(i, i + 2));
        }
        return pairs;
    }

    public void merge(Map<String, Long> before, Map<String, Long> toAdd) {
        for (Map.Entry<String, Long> newOccurrence : toAdd.entrySet()) {
            Long beforeValue = before.getOrDefault(newOccurrence.getKey(), 0L);
            before.put(newOccurrence.getKey(), beforeValue + newOccurrence.getValue());
        }
    }

    @Value
    private static class InputKey {
        String input;
        int numberOfSteps;
    }
}