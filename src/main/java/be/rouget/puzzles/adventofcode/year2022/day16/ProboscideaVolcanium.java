package be.rouget.puzzles.adventofcode.year2022.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ProboscideaVolcanium {

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

        VolcanoState start = VolcanoState.initialState("AA");
        Set<VolcanoState> states = Sets.newHashSet(start);
        
        // Explore all possibilities for 30 steps
        for (int i = 1; i <= 30; i++) {
            states = states.stream()
                    .map(VolcanoState::possibleStates)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            LOG.info("After step {}, number of states: {}", i, states.size());
        }
        
        // Keep best state
        return states.stream()
                .mapToInt(VolcanoState::pressureReleased)
                .max()
                .orElseThrow();
    }
    
    public long computeResultForPart2() {
        return -1;
    }
}