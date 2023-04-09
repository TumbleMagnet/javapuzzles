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

public class PressureLossGraphPart2 implements Graph<PressureLossStatePart2> {

    public static long computeResultForPart2(int maxTime, String nameOfStartingPosition) {
        PressureLossGraphPart2 graph = new PressureLossGraphPart2();
        PressureLossStatePart2 startState = graph.getStartState(nameOfStartingPosition);
        int minimalLoss = Dijkstra.shortestDistance(graph, startState, state -> isStateFinal(state, maxTime));

        // Best pressure release is ideal release minus minimal loss
        return (long) maxTime * Valves.maxFlowRate() - minimalLoss;
    }

    private static boolean isStateFinal(PressureLossStatePart2 state, int maxTime) {
        // State is a final state when either time is up or all valves are open 
        return state.time() >= maxTime || state.closedValves().isEmpty();
    }

    @Override
    public List<Edge<PressureLossStatePart2>> edgesFrom(PressureLossStatePart2 from) {

        // If all productive valves have been opened, just stay at current position
        if (from.closedValves().isEmpty()) {
            PressureLossStatePart2 to = new PressureLossStatePart2(from.time() + 1, from.firstPosition(), from.secondPosition(), from.closedValves(), from.currentFlowRate());
            return List.of(edge(from, to));
        }
        
        // Explore all possibilities from current position
        List<Edge<PressureLossStatePart2>> possibleMoves = Lists.newArrayList();
        List<Move> firstMoves = possibleMoves(from.firstPosition(), from.closedValves());
        List<Move> secondMoves = possibleMoves(from.secondPosition(), from.closedValves());
        for (Move firstMove : firstMoves) {
            for (Move secondMove : secondMoves) {
                if (firstMove.closedValve() == null || !firstMove.closedValve().equals(secondMove.closedValve())) {
                    Set<Valve> remainingClosedValves = from.closedValves().stream()
                            .filter(v -> !(v.equals(firstMove.closedValve()) || v.equals(secondMove.closedValve())))
                            .collect(Collectors.toSet());
                    int updatedFlowRate = from.currentFlowRate() + firstMove.flowRateIncrease() + secondMove.flowRateIncrease();
                    PressureLossStatePart2 targetState = new PressureLossStatePart2(from.time() + 1, firstMove.target(), secondMove.target(), remainingClosedValves, updatedFlowRate);
                    possibleMoves.add(edge(from, targetState));
                }
            }
        }
        return possibleMoves;
    }

    private List<Move> possibleMoves(Valve position, Set<Valve> closedValves) {
        List<Move> possibleMoves = Lists.newArrayList();
        
        // If valve at current position is closed, open it
        if (closedValves.contains(position)) {
            possibleMoves.add(new Move(position, position));
        }
        
        // Enumerate possible movements
        List<Move> movesToNeighbours = position.neighbours().stream()
                .map(Valves::findValve)
                .map(v -> new Move(v, null))
                .toList();
        possibleMoves.addAll(movesToNeighbours);

        return possibleMoves;
    }

    public PressureLossStatePart2 getStartState(String nameOfStartingPosition) {
        Set<Valve> closedValves = Valves.allValves().stream()
                .filter(v -> v.flowRate() > 0)
                .collect(Collectors.toSet());
        Valve startingValve = Valves.findValve(nameOfStartingPosition);
        return new PressureLossStatePart2(0, startingValve, startingValve, closedValves, 0);
    }
    
    private Edge<PressureLossStatePart2> edge(PressureLossStatePart2 from, PressureLossStatePart2 to) {
        return new Edge<>(from, to, Valves.maxFlowRate() - from.currentFlowRate());
    }
}
