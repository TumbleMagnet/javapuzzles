package be.rouget.puzzles.adventofcode.year2022.day19;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class NotEnoughMinerals {

    private static final Logger LOG = LogManager.getLogger(NotEnoughMinerals.class);
    private final List<Blueprint> blueprints;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(NotEnoughMinerals.class);
        NotEnoughMinerals aoc = new NotEnoughMinerals(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public NotEnoughMinerals(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        blueprints = input.stream()
                .map(Blueprint::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return blueprints.stream()
                .mapToLong(blueprint -> blueprint.index() * maxNumberOfGeodes(blueprint, 24))
                .sum();
    }

    private long maxNumberOfGeodes(Blueprint blueprint, int numberOfSteps) {
        SearchState start = SearchState.initialState(blueprint);
        Set<SearchState> states = Sets.newHashSet(start);
        for (int i = 0; i < numberOfSteps; i++) {
            final int stepIndex = i;
            LOG.info("Blueprint {} - Step {} - states: {}", blueprint.index(), i, states.size());
            states = states.stream()
                    .flatMap(state -> state.newStates(stepIndex, numberOfSteps).stream())
                    .collect(Collectors.toSet());
        }
        return states.stream()
                .mapToInt(state -> state.getQuantityForMineral(Mineral.GEODE))
                .max().orElseThrow();
    }
    
    public long computeResultForPart2() {
        List<Blueprint> remainingBlueprints = List.of(
                blueprints.get(0),
                blueprints.get(1),
                blueprints.get(2)
        );
        
        return remainingBlueprints.stream()
                .mapToLong(blueprint -> maxNumberOfGeodes(blueprint, 32))
                .reduce(1, (a, b) -> a * b);
    }
}