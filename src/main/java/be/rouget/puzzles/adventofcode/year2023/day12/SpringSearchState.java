package be.rouget.puzzles.adventofcode.year2023.day12;

import java.util.List;

public record SpringSearchState(RunningDamagedSpringGroup runningDamagedGroup, List<SpringCondition> remainingSprings) {
}
