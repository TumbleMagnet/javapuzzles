package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;

import java.util.Objects;
import java.util.Optional;

// The directionToRegion represents the direction on which the region (X) is:
// - LEFT:  X|.
// - RIGHT: .|X
// - UP:   X
//         _
//         .
// - DOWN: .
//         _
//         X
public record Side(Direction directionToRegion, Position start, Position end) {

    public Side {
        Objects.requireNonNull(directionToRegion);
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        if ((Direction.UP.equals(directionToRegion) || (Direction.DOWN.equals(directionToRegion)))
                && start.getY() != end.getY()) {
            throw new IllegalArgumentException("Horizontal sides must have same Y for both ends");
        }
        if ((Direction.LEFT.equals(directionToRegion) || (Direction.RIGHT.equals(directionToRegion)))
                && start.getX() != end.getX()) {
            throw new IllegalArgumentException("Vertical sides must have the same X for both ends");
        }
        if (end.getX() < start.getX()) {
            throw new IllegalArgumentException("Points are not ordered on the X axis");
        }
        if (end.getY() < start.getY()) {
            throw new IllegalArgumentException("Points are not ordered on the Y axis");
        }
    }

    public static Optional<Side> mergeSides(Side s1, Side s2) {
        if (!s1.directionToRegion.equals(s2.directionToRegion)) {
            // Not the same orientation or region is not on the same side
            return Optional.empty();
        }

        // Verify that sides are on the same line
        if (Direction.UP.equals(s1.directionToRegion) || (Direction.DOWN.equals(s1.directionToRegion))) {
            if (s1.start.getY() != s2.start.getY()) {
                // Horizontal sides need to have the same Y to merge
                return Optional.empty();
            }
        }
        else {
            if (s1.start.getX() != s2.start.getX()) {
                // Vertical sides need to have the same X to merge
                return Optional.empty();
            }
        }

        // Side are on the same horizontal or vertical line, merge them together if they have a point of contact
        if (s1.end.equals(s2.start)) {
            // S1 + S2
            return Optional.of(new Side(s1.directionToRegion, s1.start, s2.end));
        }
        if (s2.end.equals(s1.start)) {
            // S2 + S1
            return Optional.of(new Side(s2.directionToRegion, s2.start, s1.end));
        }

        // Side do not have a contact point
        return Optional.empty();
    }
}
