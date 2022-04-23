package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {

    @Test
    void testScramble() {
        assertThat(new Move(1, 4).scramble("bcdea")).isEqualTo("bdeac");
        assertThat(new Move(3, 0).scramble("bdeac")).isEqualTo("abdec");
    }

    @Test
    void testUnScramble() {
        assertThat(new Move(1, 4).unScramble("bdeac")).isEqualTo("bcdea");
        assertThat(new Move(3, 0).unScramble("abdec")).isEqualTo("bdeac");
    }
}