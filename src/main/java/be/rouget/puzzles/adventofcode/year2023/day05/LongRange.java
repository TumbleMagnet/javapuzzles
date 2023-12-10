package be.rouget.puzzles.adventofcode.year2023.day05;

import com.google.common.collect.Lists;

import java.util.List;

public record LongRange(long from, long to) {

    public boolean contains(int value) {
        return from <= value && value <= to;
    }

    public boolean intersects(LongRange other) {
        return !(other.to() < this.from() || other.from() > this.to());
    }

    public LongRange intersection(LongRange other) {
        if (!intersects(other)) {
            return null;
        }
        return new LongRange(Math.max(this.from(), other.from()), Math.min(this.to(), other.to()));
    }

    public List<LongRange> minus(LongRange other) {
        LongRange intersection = this.intersection(other);
        if (intersection == null) {
            return List.of(this);
        }
        List<LongRange> result = Lists.newArrayList();
        if (this.from() < intersection.from()) {
            result.add(new LongRange(this.from(), intersection.from() - 1));
        }
        if (intersection.to() < this.to()) {
            result.add(new LongRange(intersection.to()+ 1, this.to()));
        }
        return result;
    }

    public long getLength() {
        return to - from + 1;
    }
}
