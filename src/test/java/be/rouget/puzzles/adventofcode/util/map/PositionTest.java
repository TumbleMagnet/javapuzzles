package be.rouget.puzzles.adventofcode.util.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void enumerateNeighbours() {
        Assertions.assertThat(new Position(10, 20).enumerateNeighbours()).containsExactly(
            new Position( 9, 19),
            new Position( 9, 20),
            new Position( 9, 21),
            new Position(10, 19),
            new Position(10, 21),
            new Position(11, 19),
            new Position(11, 20),
            new Position(11, 21)
        );
    }
}