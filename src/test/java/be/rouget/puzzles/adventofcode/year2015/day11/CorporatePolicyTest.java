package be.rouget.puzzles.adventofcode.year2015.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2015.day11.CorporatePolicy.containsStraightOfThreeIncreasingCharacters;
import static be.rouget.puzzles.adventofcode.year2015.day11.CorporatePolicy.isValidPassword;
import static org.assertj.core.api.Assertions.assertThat;

class CorporatePolicyTest {

    @Test
    void testIsValidPassword() {

        // Not eight lower case characters
        assertThat(isValidPassword("abcffaa")).isFalse();
        assertThat(isValidPassword("abc1ffaa")).isFalse();
        assertThat(isValidPassword("abcdffaab")).isFalse();

        // Does not contain an increasing straight of 8 characters
        assertThat(isValidPassword("abbceffg")).isFalse();
        assertThat(isValidPassword("abbcegjk")).isFalse();

        // Should not contain i o or l
        assertThat(isValidPassword("abciffaa")).isFalse();
        assertThat(isValidPassword("abcoffaa")).isFalse();
        assertThat(isValidPassword("abclffaa")).isFalse();

        // Does not contains to distinct pairs
        assertThat(isValidPassword("abcdffab")).isFalse();
        assertThat(isValidPassword("abcdefab")).isFalse();

        // Valid passwords
        assertThat(isValidPassword("abcdffaa")).isTrue();
        assertThat(isValidPassword("ghjaabcc")).isTrue();
    }

    @Test
    void testContainsStraightOfThreeIncreasingCharacters() {
        assertThat(containsStraightOfThreeIncreasingCharacters("abc")).isTrue();
        assertThat(containsStraightOfThreeIncreasingCharacters("xyz")).isTrue();
        assertThat(containsStraightOfThreeIncreasingCharacters("xyzt")).isTrue();
        assertThat(containsStraightOfThreeIncreasingCharacters("txyz")).isTrue();
        assertThat(containsStraightOfThreeIncreasingCharacters("txyzs")).isTrue();

        assertThat(containsStraightOfThreeIncreasingCharacters("abd")).isFalse();
        assertThat(containsStraightOfThreeIncreasingCharacters("zyx")).isFalse();
        assertThat(containsStraightOfThreeIncreasingCharacters("ab" )).isFalse();
        assertThat(containsStraightOfThreeIncreasingCharacters(null )).isFalse();

    }

    @Test
    void testIncrement() {
        assertThat(CorporatePolicy.increment("aaaaaaaa")).isEqualTo("aaaaaaab");
        assertThat(CorporatePolicy.increment("aaaaaaaz")).isEqualTo("aaaaaaba");
        assertThat(CorporatePolicy.increment("azzzzzzz")).isEqualTo("baaaaaaa");
    }

    @Test
    void testPart1() {
        assertThat(new CorporatePolicy(List.of("abcdefgh")).computeResultForPart1()).isEqualTo("abcdffaa");
        assertThat(new CorporatePolicy(List.of("ghijklmn")).computeResultForPart1()).isEqualTo("ghjaabcc");
    }
}