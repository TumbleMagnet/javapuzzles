package be.rouget.puzzles.adventofcode.year2022.day16;

import java.util.BitSet;

public record SearchState(Valve currentPosition, int timeLeft, BitSet valvesToOpen) {
}
