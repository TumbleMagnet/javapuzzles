package be.rouget.puzzles.adventofcode.year2024.day14;

import java.util.Optional;

public record Room(int width, int height) {

    public Coordinates wrapCoordinates(Coordinates input) {
        int x = input.x();
        while (x < 0) {
            x = x + width;
        }
        while (x >= width) {
            x = x - width;
        }
        int y = input.y();
        while (y < 0) {
            y = y + height;
        }
        while (y >= height) {
            y = y - height;
        }
        return new Coordinates(x, y);
    }

    public Optional<RoomQuadrant> getQuadrant(Coordinates coordinates) {
        int medianX = width / 2;
        int medianY = height / 2;
        if (coordinates.x() < medianX && coordinates.y() < medianY) {
            return Optional.of(RoomQuadrant.NORTH_WEST);
        }
        if (coordinates.x() < medianX && coordinates.y() > medianY) {
            return Optional.of(RoomQuadrant.SOUTH_WEST);
        }
        if (coordinates.x() > medianX && coordinates.y() < medianY) {
            return Optional.of(RoomQuadrant.NORTH_EAST);
        }
        if (coordinates.x() > medianX && coordinates.y() > medianY) {
            return Optional.of(RoomQuadrant.SOUTH_EAST);
        }
        // On median lines
        return Optional.empty();
    }
}
