package be.rouget.puzzles.adventofcode.year2023.day02;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day02.Cubes.parse;
import static org.assertj.core.api.Assertions.assertThat;

class CubesTest {

    @Test
    void testParseDraw() {
        assertThat(parse("1 green, 3 red, 6 blue")).isEqualTo(new Cubes(3, 1, 6));
        assertThat(parse("6 red, 1 blue")).isEqualTo(new Cubes(6, 0, 1));
        assertThat(parse("2 green")).isEqualTo(new Cubes(0, 2, 0));
    }
}