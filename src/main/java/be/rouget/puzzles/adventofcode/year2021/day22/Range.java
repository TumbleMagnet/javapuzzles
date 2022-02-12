package be.rouget.puzzles.adventofcode.year2021.day22;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
public class Range {
    int from;
    int to;

    public static Range parse(String input) {
        String[] tokens = input.split("\\.\\.");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int value1 = Integer.parseInt(tokens[0]);
        int value2 = Integer.parseInt(tokens[1]);
        return new Range(Math.min(value1, value2), Math.max(value1, value2));
    }

    public boolean contains(int value) {
        return from <= value && value <= to;
    }

    public boolean intersects(Range other) {
        return !(other.getTo() < this.getFrom() || other.getFrom() > this.getTo());
    }

    public Range intersection(Range other) {
        if (!intersects(other)) {
            return null;
        }
        return new Range(Math.max(this.getFrom(), other.getFrom()), Math.min(this.getTo(), other.getTo()));
    }

    public List<Range> minus(Range other) {
        Range intersection = this.intersection(other);
        if (intersection == null) {
            return List.of(this);
        }
        List<Range> result = Lists.newArrayList();
        if (this.getFrom() < intersection.getFrom()) {
            result.add(new Range(this.getFrom(), intersection.getFrom() - 1));
        }
        if (intersection.getTo() < this.getTo()) {
            result.add(new Range(intersection.getTo()+ 1, this.getTo()));
        }
        return result;
    }

    public long getLength() {
        return to - from + 1;
    }
}
