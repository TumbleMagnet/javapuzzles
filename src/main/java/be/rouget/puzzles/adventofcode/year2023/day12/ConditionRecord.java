package be.rouget.puzzles.adventofcode.year2023.day12;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public record ConditionRecord(String springs, List<Integer> damagedSprings) {
    private static final Logger LOG = LogManager.getLogger(ConditionRecord.class);
    
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
        int targetTotal = damagedSprings.stream()
                .mapToInt(Integer::intValue)
                .sum();
        long result = countValidArrangements(RunningDamagedSpringGroup.emptyAtStart(), springConditions, targetTotal);
        LOG.info("Found {} arrangements for sequence {} - {}", result, springs, StringUtils.join(damagedSprings, ","));
        return result;
    }

    private long countValidArrangements(RunningDamagedSpringGroup runningDamagedGroup, List<SpringCondition> remainingSprings, int targetTotal) {

        if (remainingSprings.isEmpty()) {
            return runningDamagedGroup.matchesExactly(damagedSprings) ? 1L : 0L;
        }
        
        // Generate possible arrangements for current spring and add the number of possible arrangements for each recursively
        SpringCondition currentSpring = remainingSprings.getFirst();
        List<SpringCondition> newRemainingSprings = remainingSprings.subList(1, remainingSprings.size());
        long validCount = 0;
        for (SpringCondition sc : currentSpring.getPossibleConditions()) {
            RunningDamagedSpringGroup newRunningDamagedGroup = runningDamagedGroup.addSpring(sc);
            if (newRunningDamagedGroup.isCompatibleWith(damagedSprings, newRemainingSprings, targetTotal)) {
                validCount += countValidArrangements(newRunningDamagedGroup, newRemainingSprings, targetTotal);
            }
        }
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
