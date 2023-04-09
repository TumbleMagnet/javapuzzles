package be.rouget.puzzles.adventofcode.year2022.day16.fullgraph;

import be.rouget.puzzles.adventofcode.year2022.day16.Valve;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.*;

public record PressureLossStatePart2(int time, Valve firstPosition, Valve secondPosition, Set<Valve> closedValves, int currentFlowRate) {
    
    public PressureLossStatePart2(int time, Valve firstPosition, Valve secondPosition, Set<Valve> closedValves, int currentFlowRate) {
        this.time = time;
        this.closedValves = closedValves;
        this.currentFlowRate = currentFlowRate;

        // States with swapped positions are considered the same.
        // Implement this by sorting positions by name so that permutations end up creating the same record
        List<Valve> positions = Lists.newArrayList(firstPosition, secondPosition);
        Collections.sort(positions, Comparator.comparing(Valve::name));
        this.firstPosition = positions.get(0);
        this.secondPosition = positions.get(1);
    }

    // Override equals() and hashCode() to ignore the time field when comparing two states.
    // The "time" field is only used to know when time is up and thus to recognized "target" states of the graph
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PressureLossStatePart2 that = (PressureLossStatePart2) o;
        return currentFlowRate == that.currentFlowRate && Objects.equal(firstPosition, that.firstPosition) && Objects.equal(secondPosition, that.secondPosition) && Objects.equal(closedValves, that.closedValves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstPosition, secondPosition, closedValves, currentFlowRate);
    }
}
