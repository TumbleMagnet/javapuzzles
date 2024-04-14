package be.rouget.puzzles.adventofcode.year2023.day15;

public sealed interface Instruction permits RemoveLens, AddLens {
    
    int computeFullHash();

    String label();

    default int computeLabelHash() {
        return Hash.compute(label());
    }
}
