package be.rouget.puzzles.adventofcode.year2016.day11;

import com.google.common.collect.Sets;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class State {
    private static final int NUMBER_OF_FLOORS = 4;
    int elevatorFloor; // 0 to 3
    Set<Equipment>[] floors;

    public Set<Equipment> getEquipmentOnFloor(int floorIndex) {
        return floors[floorIndex];
    }

    public Set<Equipment> getEquipmentOnCurrentFloor() {
        return getEquipmentOnFloor(elevatorFloor);
    }

    public Set<MoveDirection> possibleMoves() {
        Set<MoveDirection> directions = Sets.newHashSet();
        if (elevatorFloor > 0) {
            directions.add(MoveDirection.DOWN);
        }
        if (elevatorFloor < NUMBER_OF_FLOORS - 1) {
            directions.add(MoveDirection.UP);
        }
        return directions;
    }

    public State moveEquipment(Set<Equipment> movedEquipments, MoveDirection direction) {

        // Validate move destination
        int destinationFloor = direction == MoveDirection.UP ? elevatorFloor + 1 : elevatorFloor -1;
        if (destinationFloor < 0 || destinationFloor >= NUMBER_OF_FLOORS) {
            throw new IllegalArgumentException("Illegal destination floor " + destinationFloor);
        }

        // Validate moved equipments
        if (movedEquipments.size() == 0) {
            throw new IllegalArgumentException("At least one equipment must be moved!");
        }
        if (movedEquipments.size() > 2) {
            throw new IllegalArgumentException("Cannot move more than 2 equipments, tried to move " + movedEquipments.size() + ": " + StringUtils.join(movedEquipments, ','));
        }
        Set<Equipment> equipmentOnCurrentFloor = getEquipmentOnFloor(elevatorFloor);
        for (Equipment equipmentToMove : movedEquipments) {
            if (!equipmentOnCurrentFloor.contains(equipmentToMove)) {
                throw new IllegalArgumentException("Illegal move: equipment on floor " + elevatorFloor + " does not contain " + equipmentToMove);
            }
        }

        // Build new State by moving objects
        Set<Equipment>[] updatedFloors = new Set[NUMBER_OF_FLOORS];
        for (int i = 0; i < NUMBER_OF_FLOORS; i++) {
            Set<Equipment> updatedFloor = Sets.newHashSet(getEquipmentOnFloor(i));
            if (i == elevatorFloor) {
                updatedFloor.removeAll(movedEquipments);
            }
            if (i == destinationFloor) {
                updatedFloor.addAll(movedEquipments);
            }
            updatedFloors[i] = updatedFloor;
        }
        return new State(destinationFloor, updatedFloors);
    }

    public boolean isValid() {
        for (int i = 0; i < NUMBER_OF_FLOORS; i++) {
            if (!validateFloor(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateFloor(int floorIndex) {
        // No chips => OK
        // No generators => OK
        // Else every chip should have its corresponding generator
        Set<Equipment> equipments = getEquipmentOnFloor(floorIndex);
        Set<Isotope> chipIsotopes = equipments.stream()
                .filter(e -> e.getType() == EquipmentType.MICROCHIP)
                .map(Equipment::getIsotope)
                .collect(Collectors.toSet());
        Set<Isotope> generatorIsotopes = equipments.stream()
                .filter(e -> e.getType() == EquipmentType.GENERATOR)
                .map(Equipment::getIsotope)
                .collect(Collectors.toSet());
        return chipIsotopes.isEmpty() || generatorIsotopes.isEmpty() || generatorIsotopes.containsAll(chipIsotopes);
    }

    public boolean isFinalState() {
        // All floors are empty except for top floor
        for (int i = 0; i < NUMBER_OF_FLOORS-1; i++) {
            if (!getEquipmentOnFloor(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return a lower-bound estimation of the remaining number of moves (to be used as heuristic in A* search)
     */
    public int estimateRemainingMoves() {
        // Add the distance of each equipment to the top floor and double it to get estimated number of moves:
        // in practice moving a generator+chip pair up one floor takes 4 moves
        int totalDistance = 0;
        for (int i = 0; i < (NUMBER_OF_FLOORS - 1); i++) {
            for (Equipment equipment : getEquipmentOnFloor(i)) {
                totalDistance += NUMBER_OF_FLOORS - 1 - i;
            }
        }
        return totalDistance;
    }
}
