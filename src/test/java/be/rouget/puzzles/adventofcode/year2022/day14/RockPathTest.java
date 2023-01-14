package be.rouget.puzzles.adventofcode.year2022.day14;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RockPathTest {

    @Test
    void parse() {
        assertThat(RockPath.parse("498,4 -> 498,6 -> 496,6")).isEqualTo(new RockPath(List.of(
                new Position(498, 4),
                new Position(498, 6),
                new Position(496, 6)
        )));
    }
}