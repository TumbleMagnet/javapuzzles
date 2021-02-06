package be.rouget.puzzles.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day1to5.AdventOfCode2015Day05.isNice2;
import static org.assertj.core.api.Assertions.assertThat;

class AdventOfCode2015Day05Test {

    @Test
    void testIsNice2() {
        assertThat(isNice2("qjhvhtzxzqqjkmpb")).isTrue();
        assertThat(isNice2("xxyxx")).isTrue();
        assertThat(isNice2("uurcxstgmygtbstg")).isFalse();
        assertThat(isNice2("ieodomkazucvgmuy")).isFalse();
    }
}