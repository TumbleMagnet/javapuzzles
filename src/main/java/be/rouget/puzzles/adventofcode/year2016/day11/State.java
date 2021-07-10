package be.rouget.puzzles.adventofcode.year2016.day11;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.GENERATOR;
import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.MICROCHIP;

@Value
public class State {
    private static final int NUMBER_OF_FLOORS = 4;
    int elevatorFloor; // 0 to 3
    EnumSet<Isotope>[] microchips;
    EnumSet<Isotope>[] generators;

    public static State createState(int elevatorFloor, Set<Equipment>[] floors) {
        return new State(elevatorFloor, extractType(floors, MICROCHIP), extractType(floors, GENERATOR));
    }

    private static EnumSet<Isotope>[] extractType(Set<Equipment>[] equipments, EquipmentType type) {
        EnumSet<Isotope>[] extracted = new EnumSet[equipments.length];
        for (int i = 0; i < equipments.length; i++) {
            extracted[i] = equipments[i].stream()
                    .filter(e -> e.getType() == type)
                    .map(Equipment::getIsotope)
                    .collect(Collectors.toCollection(() -> EnumSet.noneOf(Isotope.class)));
        }
        return extracted;
    }

    public EnumSet<Isotope> getMicrochipsOnFloor(int floorIndex) {
        return microchips[floorIndex];
    }

    public EnumSet<Isotope> getGeneratorsOnFloor(int floorIndex) {
        return generators[floorIndex];
    }

    public Set<Equipment> getEquipmentOnFloor(int floorIndex) {
        return Stream.concat(
                microchips[floorIndex].stream().map(i -> new Equipment(EquipmentType.MICROCHIP, i)),
                generators[floorIndex].stream().map(i -> new Equipment(EquipmentType.GENERATOR, i))
        ).collect(Collectors.toSet());
    }

    public Set<Equipment> getEquipmentOnCurrentFloor() {
        return getEquipmentOnFloor(elevatorFloor);
    }

    public Set<MoveDirection> possibleMoves() {
        Set<MoveDirection> directions = EnumSet.noneOf(MoveDirection.class);
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

        // Start new State by copying floor objects
        EnumSet<Isotope>[] updatedMicrochips = new EnumSet[NUMBER_OF_FLOORS];
        EnumSet<Isotope>[] updatedGenerators = new EnumSet[NUMBER_OF_FLOORS];
        for (int i = 0; i < NUMBER_OF_FLOORS; i++) {
            updatedMicrochips[i] = EnumSet.copyOf(microchips[i]);
            updatedGenerators[i] = EnumSet.copyOf(generators[i]);
        }

        // Move equipments
        for (Equipment equipmentToMove : movedEquipments) {
            switch (equipmentToMove.getType()) {
                case MICROCHIP:
                    if (!updatedMicrochips[elevatorFloor].remove(equipmentToMove.getIsotope())) {
                        throw new IllegalArgumentException("Illegal move: equipment on floor " + elevatorFloor + " does not contain " + equipmentToMove);
                    }
                    updatedMicrochips[destinationFloor].add(equipmentToMove.getIsotope());
                    break;
                case GENERATOR:
                    if (!updatedGenerators[elevatorFloor].remove(equipmentToMove.getIsotope())) {
                        throw new IllegalArgumentException("Illegal move: equipment on floor " + elevatorFloor + " does not contain " + equipmentToMove);
                    }
                    updatedGenerators[destinationFloor].add(equipmentToMove.getIsotope());
                    break;
            }
        }
        return new State(destinationFloor, updatedMicrochips, updatedGenerators);
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
        EnumSet<Isotope> chipIsotopes = microchips[floorIndex];
        EnumSet<Isotope> generatorIsotopes = generators[floorIndex];
        return chipIsotopes.isEmpty() || generatorIsotopes.isEmpty() || generatorIsotopes.containsAll(chipIsotopes);
    }

    public boolean isFinalState() {
        // All floors are empty except for top floor
        for (int i = 0; i < NUMBER_OF_FLOORS-1; i++) {
            if (!getMicrochipsOnFloor(i).isEmpty()  || !getGeneratorsOnFloor(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return a lower-bound estimation of the remaining number of moves (to be used as heuristic in A* search)
     */
    public int estimateRemainingMoves() {
        // Add the distance of each equipment to the top floor
        int totalDistance = 0;
        for (int i = 0; i < (NUMBER_OF_FLOORS - 1); i++) {
            int floorDistance = NUMBER_OF_FLOORS - 1 - i;
            totalDistance += floorDistance * (getMicrochipsOnFloor(i).size() + getGeneratorsOnFloor(i).size());
        }
        // Use that distance as heuristic
        return totalDistance;
    }
}
