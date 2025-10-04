package be.rouget.puzzles.adventofcode.year2024.day05;

import java.util.Comparator;
import java.util.List;

public record PrintRuleComparator(List<PrintRule> rules) implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        PrintRule matchingRule = rules().stream()
                .filter(r -> (o1.equals(r.first()) && o2.equals(r.second()))
                        || (o2.equals(r.first()) && o1.equals(r.second())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find rule to compare " + o1 + " and " + o2));
        return o1.equals(matchingRule.first()) ? -1 : 1;
    }
}
