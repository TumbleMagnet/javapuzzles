package be.rouget.puzzles.adventofcode.year2022.day21;

import java.util.Optional;

public interface Expression {
    long compute();
    long solve(long outcomeValue);

    default Optional<Long> computeIfPossible() {
        try {
            return Optional.of(compute());
        } catch (UnsupportedOperationException e) {
            return Optional.empty();
        }
    }

}
