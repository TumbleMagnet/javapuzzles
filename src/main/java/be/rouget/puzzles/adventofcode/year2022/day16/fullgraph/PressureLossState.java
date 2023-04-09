package be.rouget.puzzles.adventofcode.year2022.day16.fullgraph;

import be.rouget.puzzles.adventofcode.year2022.day16.Valve;
import com.google.common.base.Objects;

import java.util.Set;

public record PressureLossState(int time, Valve currentPosition, Set<Valve> closedValves, int currentFlowRate) {

    // Override equals() and hashCode() to ignore the time field when comparing two states.
    // The "time" field is only used to know when time is up and thus to recognized "target" states of the graph
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PressureLossState that = (PressureLossState) o;
        return currentFlowRate == that.currentFlowRate && Objects.equal(currentPosition, that.currentPosition) && Objects.equal(closedValves, that.closedValves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currentPosition, closedValves, currentFlowRate);
    }
}
