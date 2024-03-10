package be.rouget.puzzles.adventofcode.year2023.day12;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public record ConditionRecord(String springs, List<Integer> damagedSprings) {
    
    public ConditionRecord {
        damagedSprings = List.copyOf(damagedSprings);
    }
    
    public ConditionRecord unfold() {
        String unfoldedSprings = springs + "?" + springs + "?" + springs + "?"+ springs + "?"+ springs;
        List<Integer> unfoldedDamagedSprings = Lists.newArrayList();
        unfoldedDamagedSprings.addAll(damagedSprings);
        unfoldedDamagedSprings.addAll(damagedSprings);
        unfoldedDamagedSprings.addAll(damagedSprings);
        unfoldedDamagedSprings.addAll(damagedSprings);
        unfoldedDamagedSprings.addAll(damagedSprings);
        return new ConditionRecord(unfoldedSprings, unfoldedDamagedSprings);
    }

    public long countValidArrangements() {
        List<SpringCondition> springConditions = SpringCondition.fromSpringConditions(springs);
        return countValidArrangements(Maps.newHashMap(), RunningDamagedSpringGroup.emptyAtStart(), springConditions);
    }

    private long countValidArrangements(Map<SpringSearchState, Long> searchCache, RunningDamagedSpringGroup runningDamagedGroup, List<SpringCondition> remainingSprings) {

        SpringSearchState searchKey = new SpringSearchState(runningDamagedGroup, remainingSprings);
        Long possibleResult = searchCache.get(searchKey);
        if (possibleResult != null) {
            return possibleResult;
        }

        if (remainingSprings.isEmpty()) {
            return runningDamagedGroup.matchesExactly(damagedSprings) ? 1L : 0L;
        }
        
        // Generate possible arrangements for current spring and add the number of possible arrangements for each recursively
        SpringCondition currentSpring = remainingSprings.getFirst();
        List<SpringCondition> newRemainingSprings = remainingSprings.subList(1, remainingSprings.size());
        long validCount = 0;
        for (SpringCondition sc : currentSpring.getPossibleConditions()) {
            RunningDamagedSpringGroup newRunningDamagedGroup = runningDamagedGroup.addSpring(sc);
            if (newRunningDamagedGroup.isCompatibleWith(damagedSprings)) {
                validCount += countValidArrangements(searchCache, newRunningDamagedGroup, newRemainingSprings);
            }
        }

        searchCache.put(searchKey, validCount);
        return validCount;
    }

    public static ConditionRecord parse(String input) {
        // .##????.?.#.????? 4,1,1,3,1
        String[] tokens = input.split(" ");
        String springs = tokens[0];
        List<Integer> damagedSprings = Arrays.stream(tokens[1].split(","))
                .map(Integer::parseInt)
                .toList();
        return new ConditionRecord(springs, damagedSprings);
    }
}
