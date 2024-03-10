package be.rouget.puzzles.adventofcode.year2023.day12;

import java.util.List;

public record RunningDamagedSpringGroup(boolean inGroup, Integer groupIndex, int groupLength, int damagedCount) {
    
    public static RunningDamagedSpringGroup emptyAtStart() {
        return new RunningDamagedSpringGroup(false, null, 0, 0);
    }
    
    public RunningDamagedSpringGroup addSpring(SpringCondition springCondition) {
        if (springCondition == SpringCondition.OPERATIONAL) {
            if (inGroup) {
                // End of damaged group
                return new RunningDamagedSpringGroup(false, groupIndex, groupLength, damagedCount);
            } else {
                // No change
                return this;
            }
        } else if (springCondition == SpringCondition.DAMAGED) {
            if (inGroup) {
                // Add damaged to current group
                return new RunningDamagedSpringGroup(true, groupIndex, groupLength + 1, damagedCount + 1);
            } else {
                // Start new group
                Integer newIndex = groupIndex == null ? 0 : groupIndex + 1;
                return new RunningDamagedSpringGroup(true, newIndex, 1, damagedCount + 1);
            }
        }
        else {
            throw new IllegalArgumentException("Unexpected spring condition: " + springCondition);
        }
    }

    public boolean isCompatibleWith(List<Integer> damagedSprings, List<SpringCondition> newRemainingSprings, int targetTotal) {
        if (groupIndex == null) {
            // Not seen any damaged spring yet
            return true;
        }

        // Validate that we do not have too many groups
        if (groupIndex.intValue() >= damagedSprings.size()) {
            return false;
        }

        // Validate that size of current group does not exceed the target size for that group
        if (inGroup && groupLength > damagedSprings.get(groupIndex.intValue()).intValue()) {
            return false;
        }
        
        // Validate that size of previous group does not exceed target
        if (!inGroup && groupLength != damagedSprings.get(groupIndex.intValue()).intValue()) {
            return false;
        }

        if (damagedCount > targetTotal) {
            return false;
        }
        
        // Try to detect that target is not possible based on total number of damaged springs in target
        if (damagedCount + newRemainingSprings.size() < targetTotal) {
            // Not possible to reach target number of damaged spring
            return false;
        }

        // Too slow
//        long maxRemainingPossible = newRemainingSprings.stream()
//                .filter(sc -> sc != SpringCondition.OPERATIONAL)
//                .count();
//        if (damagedCount + maxRemainingPossible < targetTotal) {
//            // Not possible to reach target number of damaged spring
//            return false;
//        }
        
        // Looks ok
        return true;
    }
    
    public boolean matchesExactly(List<Integer> damagedSprings) {

        if (groupIndex == null) {
            return damagedSprings.size() == 0;
        }

        if (groupIndex.intValue() + 1 != damagedSprings.size()) {
            return false;
        }

        return groupLength == damagedSprings.get(groupIndex.intValue()).intValue();
    }
}
