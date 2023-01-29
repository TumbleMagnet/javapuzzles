package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ProboscideaVolcanium {

    public static final int MAX_TIME = 30;
    private static final Logger LOG = LogManager.getLogger(ProboscideaVolcanium.class);
    public static final String NAME_OF_STARTING_POSITION = "AA";

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ProboscideaVolcanium.class);
        ProboscideaVolcanium aoc = new ProboscideaVolcanium(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
//        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1Bfs());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ProboscideaVolcanium(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        List<Valve> valves = input.stream()
                .map(Valve::parse)
                .toList();
        Valves.initialize(valves);
    }

    public long computeResultForPart1Bfs() {

        VolcanoBfsState start = VolcanoBfsState.initialState(NAME_OF_STARTING_POSITION);
        Set<VolcanoBfsState> states = Sets.newHashSet(start);
        
        // Explore all possibilities for 30 steps
        for (int i = 1; i <= 30; i++) {
            states = states.stream()
                    .map(VolcanoBfsState::possibleStates)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            LOG.info("After step {}, number of states: {}", i, states.size());
        }
        
        // Keep best state
        return states.stream()
                .mapToInt(VolcanoBfsState::pressureReleased)
                .max()
                .orElseThrow();
    }

    public long computeResultForPart1() {

        // Computing the maximum pressure that can be released in 30 seconds is equivalent to find the moves which
        // amount to the minimum total loss, where the loss at a given moment is defined as the difference between
        // the ideal maximum flow rate (all valves are open) and the current flow rate.
        
        // This can be modelled as the shortest path in a graph for which:
        // - nodes are the state after some moves
        // - the distance between nodes is the loss that happens during that move
        PressureLossGraph lossGraph = new PressureLossGraph();
        int minimalLoss = Dijkstra.shortestDistance(lossGraph, lossGraph.getStartState(NAME_OF_STARTING_POSITION), state -> isStateFinal(state));
        
        // Best pressure release is ideal release minus minimal loss
        return Long.valueOf(MAX_TIME) * Valves.maxFlowRate() - minimalLoss;
    }

    private static boolean isStateFinal(PressureLossState state) {
        // State is a final state when either time is up or all valves are open 
        return state.time() >= MAX_TIME || state.currentFlowRate() == Valves.maxFlowRate();
    }

    public long computeResultForPart2() {
        return -1;
    }
}