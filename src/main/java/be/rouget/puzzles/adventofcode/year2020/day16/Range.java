package be.rouget.puzzles.adventofcode.year2020.day16;

import lombok.Value;

@Value
public class Range {
    int start;
    int end;

    public boolean contains(int value) {
        return start <= value && value <= end;
    }
}
