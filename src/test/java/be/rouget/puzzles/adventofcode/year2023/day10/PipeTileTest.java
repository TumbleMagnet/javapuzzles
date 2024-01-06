package be.rouget.puzzles.adventofcode.year2023.day10;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.util.map.Direction.*;
import static be.rouget.puzzles.adventofcode.year2023.day10.PipeTile.HORIZONTAL;
import static be.rouget.puzzles.adventofcode.year2023.day10.PipeTile.NORTH_EAST;
import static org.assertj.core.api.Assertions.assertThat;

class PipeTileTest {

    @Test
    void getIntersectingDirections() {
        assertThat(HORIZONTAL.getIntersectingDirections(UP)).containsExactlyInAnyOrder(LEFT, RIGHT);
        assertThat(HORIZONTAL.getIntersectingDirections(DOWN)).containsExactlyInAnyOrder(LEFT, RIGHT);
        assertThat(HORIZONTAL.getIntersectingDirections(LEFT)).isEmpty();
        assertThat(HORIZONTAL.getIntersectingDirections(RIGHT)).isEmpty();

        assertThat(NORTH_EAST.getIntersectingDirections(UP)).containsExactlyInAnyOrder(RIGHT);
        assertThat(NORTH_EAST.getIntersectingDirections(DOWN)).containsExactlyInAnyOrder(RIGHT);
        assertThat(NORTH_EAST.getIntersectingDirections(LEFT)).containsExactlyInAnyOrder(UP);
        assertThat(NORTH_EAST.getIntersectingDirections(RIGHT)).containsExactlyInAnyOrder(UP);
    }
}