package be.rouget.puzzles.adventofcode.year2016.day20;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FirewallRules {

    private static final Logger LOG = LogManager.getLogger(FirewallRules.class);
    private static final long MIN_IP = 0L;
    private static final long MAX_IP = 4294967295L;
    private final List<IpRange> rules;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(FirewallRules.class);
        FirewallRules aoc = new FirewallRules(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public FirewallRules(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        rules = input.stream()
                .map(IpRange::parse)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        if (!isBlocked(MIN_IP, rules)) {
            return MIN_IP;
        }
        // Return smallest of values just after each blocked range (and which is not blocked by another range)
        return rules.stream()
                .mapToLong(range -> range.getTo() + 1L)
                .filter(address -> !this.isBlocked(address, this.rules))
                .min().orElseThrow();
    }

    private boolean isBlocked(long address, List<IpRange> rules) {
        return rules.stream()
                .anyMatch(range -> range.contains(address));
    }

    public long computeResultForPart2() {

        // Merge and sort ranges
        List<IpRange> mergedRules = Lists.newArrayList();
        for (IpRange originalRule : this.rules) {
            mergedRules = merge(originalRule, mergedRules);
        }
        List<IpRange> sortedAndMergedRules = mergedRules.stream()
                .sorted(Comparator.comparing(IpRange::getFrom))
                .collect(Collectors.toList());

        // Count sizes of gaps
        long result = 0;

        // Leading gap
        long minBlocked = sortedAndMergedRules.get(0).getFrom();
        if (minBlocked > MIN_IP) {
            result += minBlocked - MIN_IP;
        }

        // Trailing gap
        long maxBlocked = sortedAndMergedRules.get(sortedAndMergedRules.size() - 1).getTo();
        if (maxBlocked < MAX_IP) {
            result += MAX_IP - maxBlocked;
        }

        // Gaps between blocked ranges
        for (int i = 0; i < sortedAndMergedRules.size() - 1; i++) {
            IpRange leftRange = sortedAndMergedRules.get(i);
            IpRange rightRange = sortedAndMergedRules.get(i+1);
            result += rightRange.getFrom() - leftRange.getTo() -1;
        }

        return result;
    }

    private List<IpRange> merge(IpRange originalRule, List<IpRange> mergedRules) {
        List<IpRange> result = Lists.newArrayList();
        IpRange newRule = originalRule;
        for (IpRange existingRule : mergedRules) {
            if (newRule.overlapsWith(existingRule)) {
                newRule = newRule.merge(existingRule);
            } else {
                result.add(existingRule);
            }
        }
        result.add(newRule);
        return result;
    }
}