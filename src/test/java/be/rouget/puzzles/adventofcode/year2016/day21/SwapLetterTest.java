package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwapLetterTest {

    @Test
    void testScramble() {
        assertThat(new SwapLetter("d", "b").scramble("ebcda")).isEqualTo("edcba");
    }

    @Test
    void testUnScramble() {
        assertThat(new SwapLetter("d", "b").unScramble("edcba")).isEqualTo("ebcda");
    }
}