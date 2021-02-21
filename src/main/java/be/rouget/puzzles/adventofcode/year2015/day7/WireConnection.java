package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

@Value
public class WireConnection implements Connection {
    String wire;

    @Override
    public boolean hasAllIncomingSignals() {
        return Circuit.getInstance().getWireSignal(wire) != null;
    }

    @Override
    public Integer computeSignal() {
        return Circuit.getInstance().getWireSignal(wire);
    }
}
