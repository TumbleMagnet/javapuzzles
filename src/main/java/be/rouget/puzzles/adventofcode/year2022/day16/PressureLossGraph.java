package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PressureLossGraph implements Graph<PressureLossState> {

    @Override
    public List<Edge<PressureLossState>> edgesFrom(PressureLossState from) {

        // If all productive valves have been opened, just stay at current position
        // TODO Is this really needed
        if (from.closedValves().isEmpty()) {
            PressureLossState to = new PressureLossState(from.time() + 1, from.currentPosition(), from.closedValves(), from.currentFlowRate());
            return List.of(edge(from, to));
        }
        
        // Explore all possibilities from current position
        List<Edge<PressureLossState>> possibleMoves = Lists.newArrayList();
        
        // First move: if current valves is closed, open it
        if (from.closedValves().contains(from.currentPosition())) {
            Set<Valve> remainingClosedValves = from.closedValves().stream()
                    .filter(v -> !v.equals(from.currentPosition()))
                    .collect(Collectors.toSet());
            int updatedFlowRate = from.currentFlowRate() + from.currentPosition().flowRate();
            PressureLossState stateAfterOpening = new PressureLossState(from.time() + 1, from.currentPosition(), remainingClosedValves, updatedFlowRate);
            possibleMoves.add(edge(from, stateAfterOpening));
        }
        
        // Other moves: explore possible tunnels to other valves
        Set<Edge<PressureLossState>> movesToNeighbourValves = from.currentPosition().neighbours().stream()
                .map(Valves::findValve)
                .map(targetValve -> new PressureLossState(from.time() + 1, targetValve, from.closedValves(), from.currentFlowRate()))
                .map(state -> edge(from, state))
                .collect(Collectors.toSet());
        possibleMoves.addAll(movesToNeighbourValves);

        return possibleMoves;
    }

    public PressureLossState getStartState(String nameOfStartingPosition) {
        Set<Valve> closedValves = Valves.allValves().stream()
                .filter(v -> v.flowRate() > 0)
                .collect(Collectors.toSet());
        return new PressureLossState(0, Valves.findValve(nameOfStartingPosition), closedValves, 0);
    }
    
    private Edge<PressureLossState> edge(PressureLossState from, PressureLossState to) {
        return new Edge<>(from, to, Valves.maxFlowRate() - from.currentFlowRate());
    }
}
