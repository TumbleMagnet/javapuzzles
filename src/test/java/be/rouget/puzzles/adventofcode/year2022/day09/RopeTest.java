package be.rouget.puzzles.adventofcode.year2022.day09;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2022.day09.Rope.moveTail;
import static org.assertj.core.api.Assertions.assertThat;

class RopeTest {

    @Test
    void testMoveTail() {
        
        // Tail does not move when close enough from head
        assertThat(moveTail(new Position(0, 0), new Position(0, 1))).isEqualTo(new Position(0, 1));
        assertThat(moveTail(new Position(0, 0), new Position(0, 0))).isEqualTo(new Position(0, 0));
        assertThat(moveTail(new Position(0, 0), new Position(0, -1))).isEqualTo(new Position(0, -1));
        assertThat(moveTail(new Position(0, 0), new Position(1, 1))).isEqualTo(new Position(1, 1));
        assertThat(moveTail(new Position(0, 0), new Position(1, 0))).isEqualTo(new Position(1, 0));
        assertThat(moveTail(new Position(0, 0), new Position(1, -1))).isEqualTo(new Position(1, -1));
        assertThat(moveTail(new Position(0, 0), new Position(-1, 1))).isEqualTo(new Position(-1, 1));
        assertThat(moveTail(new Position(0, 0), new Position(-1, 0))).isEqualTo(new Position(-1, 0));
        assertThat(moveTail(new Position(0, 0), new Position(-1, -1))).isEqualTo(new Position(-1, -1));
        
        // Head on the left
        assertThat(moveTail(new Position(0, 0), new Position(2, 0))).isEqualTo(new Position(1, 0));
        assertThat(moveTail(new Position(5, 6), new Position(8, 6))).isEqualTo(new Position(7, 6));

        // Head on the right
        assertThat(moveTail(new Position(0, 0), new Position(-2, 0))).isEqualTo(new Position(-1, 0));
        assertThat(moveTail(new Position(5, 6), new Position(3, 6))).isEqualTo(new Position(4, 6));

        // Head above
        assertThat(moveTail(new Position(0, 0), new Position(0, -2))).isEqualTo(new Position(0, -1));
        assertThat(moveTail(new Position(5, 6), new Position(5, 4))).isEqualTo(new Position(5, 5));

        // Head below
        assertThat(moveTail(new Position(0, 0), new Position(0, 2))).isEqualTo(new Position(0, 1));
        assertThat(moveTail(new Position(5, 6), new Position(5, 8))).isEqualTo(new Position(5, 7));
        
        // Tails moves in diagonal
        assertThat(moveTail(new Position(3, 2), new Position(2, 4))).isEqualTo(new Position(3, 3));
        assertThat(moveTail(new Position(4, 3), new Position(2, 4))).isEqualTo(new Position(3, 3));
    }
}