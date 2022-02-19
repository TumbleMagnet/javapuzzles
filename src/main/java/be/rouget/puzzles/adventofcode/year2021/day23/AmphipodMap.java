package be.rouget.puzzles.adventofcode.year2021.day23;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static be.rouget.puzzles.adventofcode.year2021.day23.AmphipodType.*;
import static be.rouget.puzzles.adventofcode.year2021.day23.LocationCode.*;

@Value
public class AmphipodMap {

    private static final List<Integer> HALLWAY_DESTINATIONS = List.of(0, 1, 3, 5, 7, 9, 10);

    private static int roomSize = 2;

    @Getter(value=AccessLevel.NONE)
    Set<Position> positions;

    @Getter(value=AccessLevel.NONE)
    Map<Location, Position> locationMap;

    public AmphipodMap(Set<Position> positions) {
        this.positions = positions;
        locationMap = positions.stream().collect(Collectors.toMap(Position::getLocation, Function.identity()));
    }

    public static AmphipodMap initialMapForPart1() {
        Set<Position> positions = Sets.newHashSet(
                new Position(new Location(ROOM_A, 0), BRONZE),
                new Position(new Location(ROOM_A, 1), DESERT),
                new Position(new Location(ROOM_B, 0), COPPER),
                new Position(new Location(ROOM_B, 1), DESERT),
                new Position(new Location(ROOM_C, 0), COPPER),
                new Position(new Location(ROOM_C, 1), AMBER),
                new Position(new Location(ROOM_D, 0), BRONZE),
                new Position(new Location(ROOM_D, 1), AMBER)
        );
        return new AmphipodMap(positions);
    }

    public static AmphipodMap initialMapForPart2() {
        Set<Position> positions = Sets.newHashSet(
                new Position(new Location(ROOM_A, 0), BRONZE),
                new Position(new Location(ROOM_A, 1), DESERT),
                new Position(new Location(ROOM_A, 2), DESERT),
                new Position(new Location(ROOM_A, 3), DESERT),

                new Position(new Location(ROOM_B, 0), COPPER),
                new Position(new Location(ROOM_B, 1), COPPER),
                new Position(new Location(ROOM_B, 2), BRONZE),
                new Position(new Location(ROOM_B, 3), DESERT),

                new Position(new Location(ROOM_C, 0), COPPER),
                new Position(new Location(ROOM_C, 1), BRONZE),
                new Position(new Location(ROOM_C, 2), AMBER),
                new Position(new Location(ROOM_C, 3), AMBER),

                new Position(new Location(ROOM_D, 0), BRONZE),
                new Position(new Location(ROOM_D, 1), AMBER),
                new Position(new Location(ROOM_D, 2), COPPER),
                new Position(new Location(ROOM_D, 3), AMBER)
        );
        return new AmphipodMap(positions);
    }

    public boolean isTarget() {
        return positions.stream()
                .allMatch(this::isInTargetLocation);
    }

    private boolean isInTargetLocation(Position position) {
        return position.getLocation().getLocationCode() == position.getAmphipod().getTargetRoom();
    }

    public Set<AmphipodMapWithCost> possibleMoves() {

        // If some amphoids can enter in their destination room, ignore other moves
        Set<AmphipodMapWithCost> mapsAfterMovesToDestinationRooms = computePossibleMapsFromPositions(getPositionsByLocationCode(HALLWAY));
        if (!mapsAfterMovesToDestinationRooms.isEmpty()) {
            return mapsAfterMovesToDestinationRooms;
        }

        // Otherwise, consider other moves (amphipods leaving their current room)
        return computePossibleMapsFromPositions(getPositionsInRoomsClosestToHallway());
    }

    private List<Position> getPositionsInRoomsClosestToHallway() {
        return Stream.of(ROOM_A, ROOM_B, ROOM_C, ROOM_D)
                .map(this::getPositionClosestToHallway)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Position> getPositionClosestToHallway(LocationCode room) {
        if (room == HALLWAY) {
            throw new IllegalArgumentException("Method requires a room!");
        }

        for (int i = 0; i < roomSize; i++) {
            Location roomLocation = new Location(room, i);
            Position position = locationMap.get(roomLocation);
            if (position != null) {
                return Optional.of(position);
            }
        }
        return Optional.empty();
    }

    private Set<AmphipodMapWithCost> computePossibleMapsFromPositions(List<Position> startingPositions) {
        return startingPositions.stream()
                .map(this::computeMovesForPosition)
                .flatMap(List::stream)
                .map(this::doMove)
                .collect(Collectors.toSet());
    }

    private List<Position> getPositionsByLocationCode(LocationCode... locationCodes) {
        Set<LocationCode> targetLocationCodes = Sets.newHashSet(locationCodes);
        return positions.stream()
                .filter(position -> targetLocationCodes.contains(position.getLocation().getLocationCode()))
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    public List<Move> computeMovesForPosition(Position position) {
        if (position.getLocation().getLocationCode() == HALLWAY) {
            return moveToTargetRoom(position).map(List::of).orElse(List.of());
        } else {
            return moveToHallway(position);
        }
    }

    private List<Move> moveToHallway(Position position) {
        LocationCode currentRoom = position.getLocation().getLocationCode();
        if (currentRoom == HALLWAY) {
            throw new IllegalArgumentException("This location is not in a room: " + position);
        }

        // Do not move amphipods already at their destination
        AmphipodType currentAmphipod = position.getAmphipod();
        if (currentRoom == currentAmphipod.getTargetRoom()) {
            if (position.getLocation().getIndex() == roomSize-1) {
                // Amphipod is at end its target room
                return Collections.emptyList();
            }
            // Check other room-mates further in the room: if all are of the same type, then this amphipod is in its target position
            boolean allRoommatesAreAlsoInTargetRoom = IntStream.rangeClosed(position.getLocation().getIndex(), roomSize - 1)
                    .mapToObj(i -> getAmphipodAtLocation(new Location(currentRoom, i)).orElseThrow())
                    .allMatch(amphipodType -> amphipodType.getTargetRoom() == currentRoom);
            if (allRoommatesAreAlsoInTargetRoom) {
                return Collections.emptyList();
            }
        }

        int distanceToHallway = position.getLocation().getIndex() + 1;
        List<Move> possibleMoves = Lists.newArrayList();
        Location hallwayLocationForRoom = getHallwayLocationForRoom(currentRoom);
        for (Integer targetIndexInHallway : HALLWAY_DESTINATIONS) {
            Location targetLocation = new Location(HALLWAY, targetIndexInHallway);
            if (isHallwayFree(hallwayLocationForRoom, targetLocation)) {
                int distanceInHallway = Math.abs(targetLocation.getIndex() - hallwayLocationForRoom.getIndex());
                int energyCost = (distanceInHallway + distanceToHallway) * currentAmphipod.getEnergyPerMove();
                possibleMoves.add(new Move(position, new Position(targetLocation, currentAmphipod), energyCost));
            }
        }
        return possibleMoves;
    }

    private Optional<AmphipodType> getAmphipodAtLocation(Location location) {
        Position position = locationMap.get(location);
        return Optional.ofNullable(position == null ? null : position.getAmphipod());
    }

    private Optional<Move> moveToTargetRoom(Position position) {

        if (position.getLocation().getLocationCode() != HALLWAY) {
            throw new IllegalArgumentException("Position " + position + " is not in hallway");
        }

        LocationCode targetRoom = position.getAmphipod().getTargetRoom();

        // If target room is full, move is not possible
        List<Position> positionsInTargetRoom = getPositionsByLocationCode(targetRoom);
        if (positionsInTargetRoom.size() == roomSize) {
            return Optional.empty();
        }

        // If target room contains other types of amphipods, move is not possible
        boolean containsOtherAmphipods = positionsInTargetRoom.stream().anyMatch(p -> p.getAmphipod() != position.getAmphipod());
        if (containsOtherAmphipods) {
            return Optional.empty();
        }

        // Compute target position in room
        int maxFreeIndex = IntStream.rangeClosed(0, roomSize - 1)
                .filter(index -> isFree(new Location(targetRoom, index)))
                .max().orElseThrow();
        Location targetLocationInRoom = new Location(targetRoom, maxFreeIndex);

        // Compute distance in hallway and check that the path is free
        Location hallwayTarget = getHallwayLocationForRoom(targetLocationInRoom.getLocationCode());
        if (!isHallwayFree(position.getLocation(), hallwayTarget)) {
            // Cannot move to room
            return Optional.empty();
        }
        int distanceInHallway = Math.abs(position.getLocation().getIndex() - hallwayTarget.getIndex());
        int distanceInRoom = targetLocationInRoom.getIndex() + 1;
        int distance = distanceInHallway + distanceInRoom;

        int energyCost = distance * position.getAmphipod().getEnergyPerMove();
        return Optional.of(new Move(position, new Position(targetLocationInRoom, position.getAmphipod()), energyCost));
    }

    private boolean isHallwayFree(Location start, Location target) {
        if (start.getLocationCode() != HALLWAY) {
            throw new IllegalArgumentException("Location1 is not in hallway: " + start);
        }
        if (target.getLocationCode() != HALLWAY) {
            throw new IllegalArgumentException("Location2 is not in hallway: " + target);
        }
        int minIndex = Math.min(start.getIndex(), target.getIndex());
        int maxIndex = Math.max(start.getIndex(), target.getIndex());
        for (int i = minIndex; i < maxIndex+1; i++) {
            Location currentLocation = new Location(HALLWAY, i);
            if (!currentLocation.equals(start) && !isFree(currentLocation)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFree(Location location) {
        return !locationMap.containsKey(location);
    }

    @VisibleForTesting
    public AmphipodMapWithCost doMove(Move move) {
        Set<Position> newPositions = Sets.newHashSet(this.positions);
        newPositions.remove(move.getStart());
        newPositions.add(move.getEnd());
        return new AmphipodMapWithCost(new AmphipodMap(newPositions), move.getEnergyCost());
    }

    private static Location getHallwayLocationForRoom(LocationCode room) {
        switch (room) {
            case ROOM_A: return new Location(HALLWAY, 2);
            case ROOM_B: return new Location(HALLWAY, 4);
            case ROOM_C: return new Location(HALLWAY, 6);
            case ROOM_D: return new Location(HALLWAY, 8);
            default:
                throw new IllegalArgumentException("Location " + room + " is not a room");
        }
    }

    public static void setRoomSize(int roomSize) {
        AmphipodMap.roomSize = roomSize;
    }

    @Value
    public static class Move {
        Position start;
        Position end;
        Integer energyCost;
    }


}
