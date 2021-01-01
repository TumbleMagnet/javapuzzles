package be.rouget.puzzles.adventofcode.year2020.day16;

import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class Ticket {
    List<Integer> values;

    public static Ticket fromInput(String line) {
        // 73,53,173,107,113,89,59,167,137,139,71,179,131,181,67,83,109,127,61,79
        String[] tokens = line.split(",");
        List<Integer> values = Arrays.stream(tokens).map(Integer::valueOf).collect(Collectors.toList());
        return new Ticket(values);
    }

    public boolean isValid(List<FieldRule> rules) {
        for (Integer value : values) {
            if (!matchesAtLeastOneRule(value,rules)) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesField(FieldRule rule, int fieldIndex) {
        return rule.matches(values.get(fieldIndex));
    }

    public static boolean matchesAtLeastOneRule(int value, List<FieldRule> rules) {
        FieldRule matchingRule = rules.stream()
                .filter(r -> r.matches(value))
                .findFirst()
                .orElse(null);
        return matchingRule != null;
    }

}
