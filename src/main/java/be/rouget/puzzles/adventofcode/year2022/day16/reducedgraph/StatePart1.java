package be.rouget.puzzles.adventofcode.year2022.day16.reducedgraph;

import be.rouget.puzzles.adventofcode.year2022.day16.Valve;

import java.util.Set;

public record StatePart1(Valve currentPosition, int timeLeft, Set<Valve> valvesToOpen) {
}
