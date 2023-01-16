package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;

import java.util.List;

public record Range(int from, int to) {

    public Range {
        if (to < from) {
            throw new IllegalArgumentException("Invalid boundaries: from=" + from + ", to=" + to);
        }
    }

    public boolean contains(int value) {
        return from <= value && value <= to;
    }

    public boolean intersects(Range other) {
        return !(other.to() < this.from() || other.from() > this.to());
    }

    public Range intersection(Range other) {
        if (!intersects(other)) {
            return null;
        }
        return new Range(Math.max(this.from(), other.from()), Math.min(this.to(), other.to()));
    }

    public List<Range> minus(Range other) {
        Range intersection = this.intersection(other);
        if (intersection == null) {
            return List.of(this);
        }
        List<Range> result = Lists.newArrayList();
        if (this.from() < intersection.from()) {
            result.add(new Range(this.from(), intersection.from() - 1));
        }
        if (intersection.to() < this.to()) {
            result.add(new Range(intersection.to()+ 1, this.to()));
        }
        return result;
    }

    public int getLength() {
        return to - from + 1;
    }

    public Range merge(Range other) {
        if (!this.intersects(other)) {
            throw new IllegalArgumentException("Can only merge intersecting ranges: " + this + " and " + other);
        }
        return new Range(Math.min(this.from, other.from()), Math.max(this.to, other.to()));
    }
}
