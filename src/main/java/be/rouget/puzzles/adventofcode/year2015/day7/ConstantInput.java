package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

@Value
public class ConstantInput implements Input {
    Integer value;

    @Override
    public boolean hasSignal() {
        return true;
    }

    @Override
    public Integer getSignal() {
        return value;
    }
}
