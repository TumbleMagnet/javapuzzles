package be.rouget.puzzles.adventofcode.year2022.day16.fullgraph;

import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.year2022.day16.Valve;
import be.rouget.puzzles.adventofcode.year2022.day16.Valves;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PressureLossGraph implements Graph<PressureLossState> {
    
    public static long computeResultForPart1(int maxTime, String nameOfStartingPosition) {

        // Computing the maximum pressure that can be released in 30 seconds is equivalent to find the moves which
        // amount to the minimum total loss, where the loss at a given moment is defined as the difference between
        // the ideal maximum flow rate (all valves are open) and the current flow rate.

        // This can be modelled as the shortest path in a graph for which:
        // - nodes are the state after some moves
        // - the distance between nodes is the loss that happens during that move
        PressureLossGraph graph = new PressureLossGraph();
        PressureLossState startState = graph.getStartState(nameOfStartingPosition);
        int minimalLoss = Dijkstra.shortestDistance(graph, startState, state -> isStateFinal(state, maxTime));

        // Best pressure release is ideal release minus minimal loss
        return (long) maxTime * Valves.maxFlowRate() - minimalLoss;
    }


    @Override
    public List<Edge<PressureLossState>> edgesFrom(PressureLossState from) {

        // If all productive valves have been opened, just stay at current position
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

    private static boolean isStateFinal(PressureLossState state, int maxTime) {
        // State is a final state when either time is up or all valves are open 
        return state.time() >= maxTime || state.closedValves().isEmpty();
    }
}
