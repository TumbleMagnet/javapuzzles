package be.rouget.puzzles.adventofcode.year2016.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HowAboutANiceGameOfChessTest {

    @Test
    void testPart1() {
        assertThat(new HowAboutANiceGameOfChess("abc").computeResultForPart1()).isEqualTo("18f47a30");
    }

    @Test
    void testPart2() {
        assertThat(new HowAboutANiceGameOfChess("abc").computeResultForPart2()).isEqualTo("05ace8e3");
    }
}