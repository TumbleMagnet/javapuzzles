package be.rouget.puzzles.adventofcode.year2022.day04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Range(int from, int to) {
    public Range {
        if (from > to) {
            throw new IllegalArgumentException("Invalid values for range: " + from + " - " + to);
        }
    }

    public boolean contains(Range other) {
        return this.from <= other.from && this.to >= other.to;
    }

    public boolean overlapsWith(Range other) {
        boolean isThisBeforeOther = this.to < other.from;
        boolean isOtherBeforeThis = other.to < this.from;
        return !isThisBeforeOther && !isOtherBeforeThis;
    }

    public static Range parse(String input) {
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int from = Integer.parseInt(matcher.group(1));
        int to = Integer.parseInt(matcher.group(2));
        return new Range(from, to);
    }
}
