package be.rouget.puzzles.adventofcode.year2022.day16;

import java.util.Optional;

public record Move(Valve target, Valve closedValve) {
    
    public int flowRateIncrease() {
        return Optional.ofNullable(closedValve())
                .map(Valve::flowRate)
                .orElse(0);
    }
}
