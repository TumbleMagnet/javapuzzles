package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SwapPositionTest {

    @Test
    void testScramble() {
        assertThat(new SwapPosition(4, 0).scramble("abcde")).isEqualTo("ebcda");
    }

    @Test
    void testUnScramble() {
        assertThat(new SwapPosition(4, 0).unScramble("ebcda")).isEqualTo("abcde");
    }
}