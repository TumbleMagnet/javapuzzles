package be.rouget.puzzles.adventofcode.year2020.day24;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HexDirectionTest {

    @Test
    void toDirections() {
        assertThat(HexDirection.toDirections("eeeseswneenwnwenwseeeee")).containsExactly(
                HexDirection.EAST,          // e
                HexDirection.EAST,          // e
                HexDirection.EAST,          // e
                HexDirection.SOUTH_EAST,    // se
                HexDirection.SOUTH_WEST,    // sw
                HexDirection.NORTH_EAST,    // ne
                HexDirection.EAST,          // e
                HexDirection.NORTH_WEST,    // nw
                HexDirection.NORTH_WEST,    // nw
                HexDirection.EAST,          // e
                HexDirection.NORTH_WEST,    // nw
                HexDirection.SOUTH_EAST,    // se
                HexDirection.EAST,          // e
                HexDirection.EAST,          // e
                HexDirection.EAST,          // e
                HexDirection.EAST           // e
        );
    }
}