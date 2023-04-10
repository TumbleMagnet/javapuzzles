package be.rouget.puzzles.adventofcode.year2022.day16.bitset;

import be.rouget.puzzles.adventofcode.year2022.day16.Valve;

import java.util.BitSet;

public record SearchState(Valve currentPosition, int timeLeft, BitSet valvesToOpen) {
}
