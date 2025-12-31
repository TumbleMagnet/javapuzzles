package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegionTest {

    @Test
    void computePerimeter() {

        verifyPerimeter(List.of(pos(1, 1)), 4L);

        verifyPerimeter(List.of(
                pos(1, 1),
                pos(2, 1)
        ), 6L);

        verifyPerimeter(List.of(
                pos(1, 1),
                pos(2, 1),
                pos(1, 2),
                pos(2, 2)
        ), 8L);

        verifyPerimeter(List.of(
                pos(1, 1),
                pos(1, 2),
                pos(2, 2),
                pos(2, 3)
        ), 10L);
    }

    private static void verifyPerimeter(List<Position> positions, long expectedPerimeter) {
        assertThat(new Region(new Plant("X"), positions).computePerimeter()).isEqualTo(expectedPerimeter);
    }

    @Test
    void computeNumberOfSides() {
        // Square
        verifySides(List.of(pos(1, 1)), 4L);

        // Rectangle
        verifySides(List.of(
                pos(1, 1),
                pos(2, 1)
        ), 4L);

        // Bigger square
        verifySides(List.of(
                pos(1, 1),
                pos(2, 1),
                pos(1, 2),
                pos(2, 2)
        ), 4L);

        // C.
        // CC
        // .C
        verifySides(List.of(
                pos(1, 1),
                pos(1, 2), pos(2, 2),
                                 pos(2, 3)
        ), 8L);

        // EEEEE
        // E....
        // EEEEE
        // E....
        // EEEEE
        verifySides(List.of(
                pos(1, 1), pos(2, 1), pos(3, 1), pos(4, 1), pos(5, 1),
                pos(1, 2), pos(2, 2),
                pos(1, 3), pos(2, 3), pos(3, 3), pos(4, 3), pos(5, 3),
                pos(1, 4), pos(2, 4),
                pos(1, 5), pos(2, 5), pos(3, 5), pos(4, 5), pos(5, 5)
                ), 12L);
    }

    private void verifySides(List<Position> positions, long expectedSides) {
        assertThat(new Region(new Plant("X"), positions).computeNumberOfSides()).isEqualTo(expectedSides);
    }

    private Position pos(int x, int y) {
        return new Position(x, y);
    }
}