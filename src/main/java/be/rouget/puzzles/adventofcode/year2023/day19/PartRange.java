package be.rouget.puzzles.adventofcode.year2023.day19;

import be.rouget.puzzles.adventofcode.util.Range;

public record PartRange(Range x, Range m, Range a, Range s) {

    public PartRange applyCondition(RuleCondition condition) {
        Range startingRange = getRangeForAttribute(condition.attributeName());
        Range conditionRange = condition.matchingRange();
        Range updatedRange = startingRange.intersection(conditionRange);
        if (updatedRange == null) {
            return null;
        }
        return replaceRangeForAttribute(condition.attributeName(), updatedRange);
    }

    public PartRange applyOppositeCondition(RuleCondition condition) {
        Range startingRange = getRangeForAttribute(condition.attributeName());
        Range conditionRange = condition.exludedRange();
        Range updatedRange = startingRange.intersection(conditionRange);
        if (updatedRange == null) {
            return null;
        }
        return replaceRangeForAttribute(condition.attributeName(), updatedRange);
    }
    
    public Range getRangeForAttribute(String attributeName) {
        return switch (attributeName) {
            case "x" -> x();
            case "m" -> m();
            case "a" -> a();
            case "s" -> s();
            default -> throw new IllegalArgumentException("Invalid attribute: " + attributeName);
        };
    }
    
    PartRange replaceRangeForAttribute(String attributeName, Range newRange) {
        return switch (attributeName) {
            case "x" -> new PartRange(newRange, m, a, s);
            case "m" -> new PartRange(x, newRange, a, s);
            case "a" -> new PartRange(x, m, newRange, s);
            case "s" -> new PartRange(x, m, a, newRange);
            default -> throw new IllegalArgumentException("Invalid attribute: " + attributeName);
        };
    }
    
    public long countCombinations() {
        return ((long) x().getLength())
                * ((long) m().getLength())
                * ((long) a().getLength())
                * ((long) s().getLength());
    }
}
