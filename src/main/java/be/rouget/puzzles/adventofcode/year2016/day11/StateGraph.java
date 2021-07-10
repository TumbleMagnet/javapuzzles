package be.rouget.puzzles.adventofcode.year2016.day11;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StateGraph implements Graph<State> {

    @Override
    public List<Edge<State>> edgesFrom(State from) {

        // Generate all possible moves and keep only valid target states
        Set<State> targetStates = Sets.newHashSet();
        for (MoveDirection direction : from.possibleMoves()) {
            for (Set<Equipment> equipmentsToMove : pickUpOneTwoEquipments(from.getEquipmentOnCurrentFloor())) {
                State targetState = from.moveEquipment(equipmentsToMove, direction);
                if (targetState.isValid()) {
                    targetStates.add(targetState);
                }
            }
        }

        // Generate edges to possible states
        return targetStates.stream()
                .map(state -> new Edge<State>(from, state, 1))
                .collect(Collectors.toList());
    }

    private Set<Set<Equipment>> pickUpOneTwoEquipments(Set<Equipment> equipments) {
        Set<Set<Equipment>> possibleChoices = Sets.newHashSet();

        // Add possibilities with one equipment
        for (Equipment equipment : equipments) {
            possibleChoices.add(Sets.newHashSet(equipment));
        }

        // Add possibilities with two equipements
        for (Equipment equipment1 : equipments) {
            for (Equipment equipment2 : equipments) {
                if (!equipment2.equals(equipment1)) {
                    possibleChoices.add(Sets.newHashSet(equipment1, equipment2));
                }
            }
        }

        return possibleChoices;
    }
}
