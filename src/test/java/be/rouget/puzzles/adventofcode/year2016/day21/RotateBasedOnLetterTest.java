package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RotateBasedOnLetterTest {

    @Test
    void testScramble() {
        assertThat(new RotateBasedOnLetter("b").scramble("abdec")).isEqualTo("ecabd");
        assertThat(new RotateBasedOnLetter("d").scramble("ecabd")).isEqualTo("decab");
    }

    @Test
    void testUnScramble() {
        assertThat(new RotateBasedOnLetter("b").unScramble("ecabd")).isEqualTo("abdec");
        assertThat(new RotateBasedOnLetter("d").unScramble("decab")).isEqualTo("ecabd");
    }

}