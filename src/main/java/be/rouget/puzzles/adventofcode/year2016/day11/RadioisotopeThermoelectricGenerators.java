package be.rouget.puzzles.adventofcode.year2016.day11;

import be.rouget.puzzles.adventofcode.util.graph.AStar;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.GENERATOR;
import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.MICROCHIP;
import static be.rouget.puzzles.adventofcode.year2016.day11.Isotope.*;

public class RadioisotopeThermoelectricGenerators {

    private static final Logger LOG = LogManager.getLogger(RadioisotopeThermoelectricGenerators.class);

    public static void main(String[] args) {
        LOG.info("Starting...");
        RadioisotopeThermoelectricGenerators aoc = new RadioisotopeThermoelectricGenerators();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public RadioisotopeThermoelectricGenerators() {
    }

    public long computeResultForPart1() {
        return numberOfMovesToFinalState(getStartState()); // Runs in about 2 seconds
    }

    public long computeResultForPart2() {
        State startState = getStartState();

        // Upon entering the isolated containment area, however, you notice some extra parts on the first floor that weren't listed on the record outside:
        // - An elerium generator.
        // - An elerium-compatible microchip.
        // - A dilithium generator.
        // - A dilithium-compatible microchip.
        Set<Equipment> additionalEquipment = Sets.newHashSet(
                new Equipment(GENERATOR, ELERIUM),
                new Equipment(MICROCHIP, ELERIUM),
                new Equipment(GENERATOR, DILITHIUM),
                new Equipment(MICROCHIP, DILITHIUM)
        );
        startState.getMicrochipsOnFloor(0).add(ELERIUM);
        startState.getMicrochipsOnFloor(0).add(DILITHIUM);
        startState.getGeneratorsOnFloor(0).add(ELERIUM);
        startState.getGeneratorsOnFloor(0).add(DILITHIUM);
        return numberOfMovesToFinalState(startState); // Runs in about 2 minutes 30 second and 10 Gb memory
    }

    private State getStartState() {
        // The first floor contains a polonium generator, a thulium generator, a thulium-compatible microchip,
        // a promethium generator, a ruthenium generator, a ruthenium-compatible microchip, a cobalt generator,
        // and a cobalt-compatible microchip.
        Set<Equipment> firstFloor = Sets.newHashSet(
                new Equipment(GENERATOR, POLONIUM),
                new Equipment(GENERATOR, THULIUM),
                new Equipment(MICROCHIP, THULIUM),
                new Equipment(GENERATOR, PROMETHIUM),
                new Equipment(GENERATOR, RUTHENIUM),
                new Equipment(MICROCHIP, RUTHENIUM),
                new Equipment(GENERATOR, COBALT),
                new Equipment(MICROCHIP, COBALT)
        );
        // The second floor contains a polonium-compatible microchip and a promethium-compatible microchip.
        Set<Equipment> secondFloor = Sets.newHashSet(
                new Equipment(MICROCHIP, POLONIUM),
                new Equipment(MICROCHIP, PROMETHIUM)
        );
        // The third floor contains nothing relevant.
        // The fourth floor contains nothing relevant.

        Set<Equipment>[] floors = new Set[] {
                firstFloor,
                secondFloor,
                Sets.newHashSet(),
                Sets.newHashSet()
        };
        return State.createState(0, floors);
    }

    public int numberOfMovesToFinalState(State startState) {
        // Search for shortest path from start to final state
        return AStar.shortestDistance(new StateGraph(), startState, State::isFinalState, State::estimateRemainingMoves);
    }

}