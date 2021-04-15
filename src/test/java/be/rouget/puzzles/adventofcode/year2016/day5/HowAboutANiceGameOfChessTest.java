package be.rouget.puzzles.adventofcode.year2016.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HowAboutANiceGameOfChessTest {

//    @Test
//    void testHash() {
//        assertThat(newhash("abc3231929")).startsWith("000001");
//        assertThat(hash("abc5017308")).startsWith("000008f82");
//        assertThat(hash("abc5278568")).startsWith("00000f");
//    }

    @Test
    void testPart1() {
        assertThat(new HowAboutANiceGameOfChess("abc").computeResultForPart1()).isEqualTo("18f47a30");
    }
}