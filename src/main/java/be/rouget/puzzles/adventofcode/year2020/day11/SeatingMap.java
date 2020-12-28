package be.rouget.puzzles.adventofcode.year2020.day11;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SeatingMap {

    private final int width;
    private final int height;
    private final Map<Position, SeatStatus> seats;

    public SeatingMap(List<String> input) {

        String[] lines = input.toArray(new String[0]);
        this.height = lines.length;
        this.width = lines[0].length();
        this.seats = Maps.newHashMap();

        for (int y = 0; y < height; y++) {
            String currentLine = lines[y];
            if (currentLine.length() != width) {
                throw new IllegalArgumentException("Line " + (y+1) + " has length " + currentLine.length() + " while expecting " + this.width);
            }
            String[] characters = AocStringUtils.splitCharacters(currentLine);
            for (int x = 0; x < characters.length; x++) {
                Position position = new Position(x, y);
                seats.put(position, SeatStatus.fromMapChar(characters[x]));
            }
        }
    }

    public SeatingMap(int width, int height, Map<Position, SeatStatus> seats) {
        this.width = width;
        this.height = height;
        this.seats = seats;
    }

    public SeatingMap predictNextMap() {
        return predictNextMap(this::nextStatus);
    }

    public SeatingMap predictNextMap2() {
        return predictNextMap(this::nextStatus2);
    }

    public SeatingMap predictNextMap(Function<Position, SeatStatus> nextStatusFunction) {
        Map<Position, SeatStatus> newSeats = Maps.newHashMap();
        seats.keySet().forEach(position -> newSeats.put(position, nextStatusFunction.apply(position)));
        return new SeatingMap(width, height, newSeats);
    }

    private SeatStatus nextStatus(Position p) {
        SeatStatus currentStatus = getStatusAt(p);
        switch (currentStatus) {
            case FLOOR:
                return SeatStatus.FLOOR;
            case FREE_SEAT:
                if (countAdjacentOccupiedSeats(p) == 0) {
                    return SeatStatus.OCCUPIED_SEAT;
                } else {
                    return SeatStatus.FREE_SEAT;
                }
            case OCCUPIED_SEAT:
                if (countAdjacentOccupiedSeats(p) >= 4) {
                    return SeatStatus.FREE_SEAT;
                } else {
                    return SeatStatus.OCCUPIED_SEAT;
                }
        }
        throw new IllegalStateException("Did not expect seat status " + currentStatus);
    }

    public int countAdjacentOccupiedSeats(Position position) {
        int count = 0;
        for (Direction direction : Direction.values()) {
            Position adjacentPosition = position.next(direction);
            if (isPositionInMap(adjacentPosition)
                    && getStatusAt(adjacentPosition) == SeatStatus.OCCUPIED_SEAT) {
                count++;
            }
        }
        return count;
    }

    private SeatStatus nextStatus2(Position p) {
        SeatStatus currentStatus = getStatusAt(p);
        switch (currentStatus) {
            case FLOOR:
                return SeatStatus.FLOOR;
            case FREE_SEAT:
                if (countVisibleOccupiedSeats(p) == 0) {
                    return SeatStatus.OCCUPIED_SEAT;
                } else {
                    return SeatStatus.FREE_SEAT;
                }
            case OCCUPIED_SEAT:
                if (countVisibleOccupiedSeats(p) >= 5) {
                    return SeatStatus.FREE_SEAT;
                } else {
                    return SeatStatus.OCCUPIED_SEAT;
                }
        }
        throw new IllegalStateException("Did not expect seat status " + currentStatus);
    }

    private int countVisibleOccupiedSeats(Position startingPosition) {
        int count = 0;
        for (Direction direction : Direction.values()) {
            Optional<SeatStatus> firstVisibleSeat = getFirstVisibleSeat(startingPosition, direction);
            if (firstVisibleSeat.isPresent() && firstVisibleSeat.get() == SeatStatus.OCCUPIED_SEAT) {
                count++;
            }
        }
        return count;
    }

    private Optional<SeatStatus> getFirstVisibleSeat(Position position, Direction direction) {
        Position currentPosition = position.next(direction);
        while (isPositionInMap(currentPosition)) {
            SeatStatus currentStatus = getStatusAt(currentPosition);
            if (currentStatus != SeatStatus.FLOOR) {
                return Optional.of(currentStatus);
            }
            currentPosition = currentPosition.next(direction);
        }
        return Optional.empty();
    }

    private boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < width
                && position.getY() >= 0 && position.getY() < height;
    }

    public int countOccupiedSeats() {
        return seats.values().stream()
                .mapToInt(seatStatus -> seatStatus == SeatStatus.OCCUPIED_SEAT ? 1 : 0)
                .sum();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SeatStatus getStatusAt(Position position) {
        if (!isPositionInMap(position)) {
            throw new IllegalArgumentException("Position " + position + " is not in map!");
        }
        return seats.get(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatingMap that = (SeatingMap) o;
        return width == that.width && height == that.height && seats.equals(that.seats);
    }
}
