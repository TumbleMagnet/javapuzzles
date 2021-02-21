package be.rouget.puzzles.adventofcode.year2015.day7;

public interface Connection {
    boolean hasAllIncomingSignals();
    Integer computeSignal();
}
