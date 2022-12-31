package be.rouget.puzzles.adventofcode.year2022.day04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record AssignmentPair(Range first, Range second) {

    public boolean hasOneRangeContainedInTheOther() {
        return first.contains(second) || second.contains(first);
    }

    public boolean hasOverlappingRanges() {
        return first.overlapsWith(second);
    }

    public static AssignmentPair parse(String input) {
        Pattern pattern = Pattern.compile("([^,]*),([^,]*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        Range range1 = Range.parse(matcher.group(1));
        Range range2 = Range.parse(matcher.group(2));
        return new AssignmentPair(range1, range2);
    }
}
