package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

@Value
public class WireInput implements Input {
    String wire;

    @Override
    public boolean hasSignal() {
        return Circuit.getInstance().getWireSignal(wire) != null;
    }

    @Override
    public Integer getSignal() {
        return Circuit.getInstance().getWireSignal(wire);
    }
}
