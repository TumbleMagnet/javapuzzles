package be.rouget.puzzles.adventofcode.year2015.day10;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day10.ElvesLookElvesSay.lookAndSay;
import static org.assertj.core.api.Assertions.assertThat;


class ElvesLookElvesSayTest {

    @Test
    void testLookAndSay() {
        assertThat(lookAndSay("1")).isEqualTo("11");
        assertThat(lookAndSay("11")).isEqualTo("21");
        assertThat(lookAndSay("21")).isEqualTo("1211");
        assertThat(lookAndSay("1211")).isEqualTo("111221");
        assertThat(lookAndSay("111221")).isEqualTo("312211");
    }
}