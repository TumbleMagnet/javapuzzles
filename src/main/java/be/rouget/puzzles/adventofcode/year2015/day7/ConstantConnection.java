package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

@Value
public class ConstantConnection implements Connection {
    Integer value;

    @Override
    public boolean hasAllIncomingSignals() {
        return true;
    }

    @Override
    public Integer computeSignal() {
        return value;
    }
}
