package be.rouget.puzzles.adventofcode.year2022.day19;

import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

public record SearchState(Quantity robots, Quantity minerals, Blueprint blueprint, EnumSet<Mineral> robotsToSkip) {

    public static SearchState initialState(Blueprint blueprint) {
        return new SearchState(new Quantity(1, 0, 0, 0), new Quantity(0, 0, 0, 0), blueprint, EnumSet.noneOf(Mineral.class));
    }

    public int getQuantityForMineral(Mineral mineral) {
        return minerals.getQuantityForMineral(mineral);
    }

    public Set<SearchState> newStates(int stepIndex, int numberOfSteps) {

        Set<SearchState> newStates = Sets.newHashSet();
        
        // Try to produce some robots (but not on last turn)
        EnumSet<Mineral> possibleRobots = EnumSet.noneOf(Mineral.class);
        if (stepIndex < (numberOfSteps -1)) { // Optimization: no need to build a robot on the last step
            for (Mineral targetMineral : Mineral.values()) {
                if (robotsToSkip== null || !robotsToSkip.contains(targetMineral)) {
                    Quantity robotCost = blueprint.costForRobot(targetMineral);
                    Quantity mineralsAfterBuyingRobot = minerals.minus(robotCost);
                    if (!mineralsAfterBuyingRobot.isNegative()
                            && blueprint.isOneAdditionalRobotUseful(robots, targetMineral, numberOfSteps-stepIndex-1)) { // Optimization: do not build robot for resources we already produce enough of
                        newStates.add(new SearchState(robots.add(1, targetMineral), mineralsAfterBuyingRobot.add(robots), blueprint, null));
                        possibleRobots.add(targetMineral);
                    }
                }
            }
        }

        // Additional state: produce no robot.
        // Optimizations:
        // - if we can produce all the robots when produce one
        // - if we decide not to produce a robot that was possible now, it does not make sense to produce that same robot first in future steps (result cannot be better).
        if (!EnumSet.complementOf(possibleRobots).isEmpty()) {
            EnumSet<Mineral> newRobotsToSkip = robotsToSkip == null ? EnumSet.noneOf(Mineral.class) : EnumSet.copyOf(robotsToSkip);
            newRobotsToSkip.addAll(possibleRobots);
            newStates.add(new SearchState(robots, minerals.add(robots), blueprint, newRobotsToSkip.isEmpty() ? null : newRobotsToSkip));
        }

        return newStates;
    }
    
    public int computeMaximumPossibleNumberOfGeodes(int numberOfStepsRemaining) {

        int max = computeMinimumNumberOfGeodes(numberOfStepsRemaining);

        // Assume one additional robot per remaining turn so 0 + 1 + 2 + 3, etc
        if (numberOfStepsRemaining > 1) {
            max += (numberOfStepsRemaining * (numberOfStepsRemaining - 1)) / 2;
        }
        return max;
    }

    public int computeMinimumNumberOfGeodes(int numberOfStepsRemaining) {
        // For sure: geode already produced plus production of geode robots
        return getQuantityForMineral(Mineral.GEODE) + numberOfStepsRemaining * robots.getQuantityForMineral(Mineral.GEODE);
    }
}
