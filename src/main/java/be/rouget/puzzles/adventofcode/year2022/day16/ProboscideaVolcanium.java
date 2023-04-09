package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.year2022.day16.fullgraph.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class ProboscideaVolcanium {

    public static final int MAX_TIME = 30;
    public static final int MAX_TIME_PART2 = MAX_TIME - 4;
    public static final String NAME_OF_STARTING_POSITION = "AA";

    private static final Logger LOG = LogManager.getLogger(ProboscideaVolcanium.class);

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ProboscideaVolcanium.class);
        ProboscideaVolcanium aoc = new ProboscideaVolcanium(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ProboscideaVolcanium(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        List<Valve> valves = input.stream()
                .map(Valve::parse)
                .toList();
        Valves.initialize(valves);
    }

    public long computeResultForPart1() {

        // Computing the maximum pressure that can be released in 30 seconds is equivalent to find the moves which
        // amount to the minimum total loss, where the loss at a given moment is defined as the difference between
        // the ideal maximum flow rate (all valves are open) and the current flow rate.
        
        // This can be modelled as the shortest path in a graph for which:
        // - nodes are the state after some moves
        // - the distance between nodes is the loss that happens during that move
        PressureLossGraph graph = new PressureLossGraph();
        PressureLossState startState = graph.getStartState(NAME_OF_STARTING_POSITION);
        int minimalLoss = Dijkstra.shortestDistance(graph, startState, state -> isStateFinal(state, MAX_TIME));

        // Best pressure release is ideal release minus minimal loss
        return (long) MAX_TIME * Valves.maxFlowRate() - minimalLoss;
    }

    public long computeResultForPart2() {
        PressureLossGraphPart2 graph = new PressureLossGraphPart2();
        PressureLossStatePart2 startState = graph.getStartState(NAME_OF_STARTING_POSITION);
        int minimalLoss = Dijkstra.shortestDistance(graph, startState, state -> isStateFinal(state, MAX_TIME_PART2));

        // Best pressure release is ideal release minus minimal loss
        return (long) MAX_TIME_PART2 * Valves.maxFlowRate() - minimalLoss;
    }

    private static boolean isStateFinal(PressureLossState state, int maxTime) {
        // State is a final state when either time is up or all valves are open 
        return state.time() >= maxTime || state.closedValves().isEmpty();
    }

    private static boolean isStateFinal(PressureLossStatePart2 state, int maxTime) {
        // State is a final state when either time is up or all valves are open 
        return state.time() >= maxTime || state.closedValves().isEmpty();
    }
}