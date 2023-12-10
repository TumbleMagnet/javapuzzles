package be.rouget.puzzles.adventofcode.year2023.day05;

import com.google.common.collect.Lists;

import java.util.List;

public record MappingLine(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
    
    public static MappingLine parse(String input) {
        // 3917364646 2257358228 17288082
        String[] tokens = input.split(" ");
        long destinationRangeStart = Long.parseLong(tokens[0]);
        long sourceRangeStart = Long.parseLong(tokens[1]);
        long rangeLength = Long.parseLong(tokens[2]);
        return new MappingLine(destinationRangeStart, sourceRangeStart, rangeLength);
    }

    public boolean containsSource(long source) {
        return source >= sourceRangeStart && source <= sourceRangeStart + rangeLength -1;
    }

    public long mapSourceToDestination(long source) {
        if (!containsSource(source)) {
            throw new IllegalArgumentException("Source " + source + " is not in this mapping range: " + this);
        }
        return destinationRangeStart + source - sourceRangeStart;
    }

    public MappedRanges mapRange(LongRange inputRange) {

        // Parts of input range outside the source range of this mapping are not affected
        List<LongRange> outsideRanges = inputRange.minus(this.getSourceRange());

        // Part of input range within the source range of this mapping gets mapped
        List<LongRange> mappedRanges = Lists.newArrayList();
        LongRange insideRange = inputRange.intersection(this.getSourceRange());
        if (insideRange != null) {
            LongRange mappedRange = new LongRange(destinationRangeStart + insideRange.from() - sourceRangeStart, destinationRangeStart + insideRange.to() - sourceRangeStart);
            mappedRanges.add(mappedRange);
        }
        return new MappedRanges(outsideRanges, mappedRanges);
    }

    private LongRange getSourceRange() {
        return new LongRange(this.sourceRangeStart, this.sourceRangeStart + rangeLength - 1);
    }
}
