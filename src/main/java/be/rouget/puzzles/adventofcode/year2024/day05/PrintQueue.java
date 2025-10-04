package be.rouget.puzzles.adventofcode.year2024.day05;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class PrintQueue {

    private static final Logger LOG = LogManager.getLogger(PrintQueue.class);
    private final List<List<Integer>> updates;
    private final PrintRuleComparator comparator;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PrintQueue.class);
        PrintQueue aoc = new PrintQueue(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PrintQueue(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        // Parse rules
        List<PrintRule> rules = input.stream()
                .filter(s -> s.contains("|"))
                .map(PrintRule::parse)
                .toList();
        LOG.info("Found {} rules...", rules.size());

        // Prepare the comparator
        comparator = new PrintRuleComparator(rules);

        // Parse updates
        updates = input.stream()
                .filter(line -> line.contains(","))
                .map(this::parsePageNumbers)
                .toList();
        LOG.info("Found {} updates...", updates.size());

    }

    private List<Integer> parsePageNumbers(String updateAsCsvLine) {
        String[] tokens = updateAsCsvLine.split(",");
        return Arrays.stream(tokens)
                .map(Integer::parseInt)
                .toList();
    }

    public long computeResultForPart1() {
        return updates.stream()
                .filter(update -> isSorted(update, comparator))
                .mapToInt(PrintQueue::getMedianElement)
                .sum();
    }

    public long computeResultForPart2() {
        return updates.stream()
                .filter(update -> !isSorted(update, comparator))
                .map(update -> sort(update, comparator))
                .mapToInt(PrintQueue::getMedianElement)
                .sum();
    }

    private static boolean isSorted(List<Integer> elements, Comparator<Integer> comparator) {
        if (CollectionUtils.isEmpty(elements) || elements.size() == 1) {
            return true;
        }

        Iterator<Integer> iter = elements.iterator();
        Integer previous = iter.next();
        Integer current;
        while (iter.hasNext()) {
            current = iter.next();
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    private static List<Integer> sort(List<Integer> elements, Comparator<Integer> comparator) {
        List<Integer> result = Lists.newArrayList(elements);
        result.sort(comparator);
        return result;
    }

    public static int getMedianElement(List<Integer> update) {
        int medianIndex = update.size() / 2;
        return update.get(medianIndex);
    }

}