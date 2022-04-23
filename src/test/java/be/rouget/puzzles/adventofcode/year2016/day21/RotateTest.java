package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RotateTest {

    @Test
    void testScramble() {
        assertThat(new Rotate(Direction.LEFT, 1).scramble("abcde")).isEqualTo("bcdea");
        assertThat(new Rotate(Direction.LEFT, 1).scramble("bcdea")).isEqualTo("cdeab");
        assertThat(new Rotate(Direction.LEFT, 2).scramble("abcde")).isEqualTo("cdeab");
        assertThat(new Rotate(Direction.RIGHT, 1).scramble("abcd")).isEqualTo("dabc");

        assertThat(new Rotate(Direction.LEFT, 0).scramble("abcde")).isEqualTo("abcde");
        assertThat(new Rotate(Direction.RIGHT, 0).scramble("abcde")).isEqualTo("abcde");
    }

    @Test
    void testUnScramble() {
        assertThat(new Rotate(Direction.LEFT, 1).unScramble("bcdea")).isEqualTo("abcde");
        assertThat(new Rotate(Direction.LEFT, 1).unScramble("cdeab")).isEqualTo("bcdea");
        assertThat(new Rotate(Direction.LEFT, 2).unScramble("cdeab")).isEqualTo("abcde");
        assertThat(new Rotate(Direction.RIGHT, 1).unScramble("dabc")).isEqualTo("abcd");

        assertThat(new Rotate(Direction.LEFT, 0).unScramble("abcde")).isEqualTo("abcde");
        assertThat(new Rotate(Direction.RIGHT, 0).unScramble("abcde")).isEqualTo("abcde");
    }

}