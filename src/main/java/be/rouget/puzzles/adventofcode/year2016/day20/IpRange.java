package be.rouget.puzzles.adventofcode.year2016.day20;

import lombok.Value;

@Value
public class IpRange {
    long from;
    long to;

    public boolean contains(long address) {
        return address >= from && address <= to;
    }

    public boolean overlapsWith(IpRange other) {
        boolean isThisBeforeOther = this.to < other.from;
        boolean isOtherBeforeThis = other.to < this.from;
        return !isThisBeforeOther && !isOtherBeforeThis;
    }

    public IpRange merge(IpRange other) {
        if (!overlapsWith(other)) {
            throw new IllegalArgumentException("Cannot merge ranges which do not overlap");
        }
        return new IpRange(Math.min(this.from, other.from), Math.max(this.to, other.to));
    }

    public static IpRange parse(String input) {
        String[] tokens = input.split("-");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        long from = Long.parseLong(tokens[0]);
        long to = Long.parseLong(tokens[1]);
        if (from > to) {
            throw new IllegalArgumentException("From " + from + " must be lower than to " + to);
        }
        return new IpRange(from, to);
    }
}
