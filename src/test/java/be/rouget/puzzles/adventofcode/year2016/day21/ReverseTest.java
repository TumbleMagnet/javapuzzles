package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReverseTest {

    @Test
    void testScramble() {
        assertThat(new Reverse(0, 4).scramble("edcba")).isEqualTo("abcde");
        assertThat(new Reverse(1, 3).scramble("abcde")).isEqualTo("adcbe");
    }

    @Test
    void testUnScramble() {
        assertThat(new Reverse(0, 4).unScramble("abcde")).isEqualTo("edcba");
        assertThat(new Reverse(1, 3).unScramble("adcbe")).isEqualTo("abcde");
    }

}