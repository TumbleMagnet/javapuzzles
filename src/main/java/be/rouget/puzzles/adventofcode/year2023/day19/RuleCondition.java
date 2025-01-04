package be.rouget.puzzles.adventofcode.year2023.day19;

import be.rouget.puzzles.adventofcode.util.Range;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RuleCondition(String attributeName, ConditionOperator operator, int targetValue) {

    public static RuleCondition parse(String input) {
        // x<3088
        Pattern pattern = Pattern.compile("(.*)([<>])(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String attributeName = matcher.group(1);
        ConditionOperator operator = ConditionOperator.fromInput(matcher.group(2));
        int targetValue = Integer.parseInt(matcher.group(3));
        return new RuleCondition(attributeName, operator, targetValue);
    }
    
    public boolean evaluate(Part part) {
        int partValue = part.getValue(attributeName());
        return switch (operator) {
            case GREATER_THAN -> partValue > targetValue;
            case SMALLER_THAN -> partValue < targetValue;
        };
    }

    public Range matchingRange() {
        return switch (operator) {
            case GREATER_THAN -> new Range(Math.min(targetValue+1, 4000), 4000);
            case SMALLER_THAN -> new Range(0, Math.max(targetValue-1, 0));
        };
    }
    
    public Range exludedRange() {
        List<Range> excludedRanges = new Range(0, 4000).minus(matchingRange());
        if (excludedRanges.size() != 1) {
            throw new IllegalArgumentException("Cannot compute excluded range for condition: " + this);
        }
        return excludedRanges.get(0);
    }
}
