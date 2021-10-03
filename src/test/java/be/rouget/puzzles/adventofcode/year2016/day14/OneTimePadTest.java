package be.rouget.puzzles.adventofcode.year2016.day14;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day14.OneTimePad.extractRepeatedChars;
import static org.assertj.core.api.Assertions.assertThat;

class OneTimePadTest {

    @Test
    void testHash() {
        OneTimePad oneTimePad = new OneTimePad("abc");
        assertThat(oneTimePad.hash(18, oneTimePad::md5Hash)).contains("cc38887a5");
        assertThat(oneTimePad.hash(39, oneTimePad::md5Hash)).contains("eee");
        assertThat(oneTimePad.hash(816, oneTimePad::md5Hash)).contains("eeeee");
        assertThat(oneTimePad.hash(92, oneTimePad::md5Hash)).contains("999");
        assertThat(oneTimePad.hash(200, oneTimePad::md5Hash)).contains("99999");
    }

    @Test
    void testPart1() {
        OneTimePad oneTimePad = new OneTimePad("abc");
        assertThat(oneTimePad.computeResultForPart1()).isEqualTo(22728);
    }

    @Test
    void testPart2() {
        OneTimePad oneTimePad = new OneTimePad("abc");
        assertThat(oneTimePad.computeResultForPart2()).isEqualTo(22551);
    }

    @Test
    void testExtractRepeatedChars() {
        assertThat(extractRepeatedChars("azertyuiop", 3)).isEmpty();
        assertThat(extractRepeatedChars("azerryuiop", 3)).isEmpty();
        assertThat(extractRepeatedChars("azerrruiop", 3)).containsExactlyInAnyOrder("r");
        assertThat(extractRepeatedChars("aaarfnuiop", 3)).containsExactlyInAnyOrder("a");
        assertThat(extractRepeatedChars("qsdfghhmmm", 3)).containsExactlyInAnyOrder("m");
        assertThat(extractRepeatedChars("azerrruiii", 3)).containsExactlyInAnyOrder("r", "i");
        assertThat(extractRepeatedChars("azrrrrriop", 3)).containsExactlyInAnyOrder("r");
        assertThat(extractRepeatedChars("azrrrrriii", 5)).containsExactlyInAnyOrder("r");
    }
}