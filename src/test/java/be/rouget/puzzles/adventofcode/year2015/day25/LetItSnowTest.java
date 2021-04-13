package be.rouget.puzzles.adventofcode.year2015.day25;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day25.LetItSnow.nextCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LetItSnowTest {

    @Test
    void testNextCode() {
        assertThat(nextCode(20151125L)).isEqualTo(31916031L);
        assertThat(nextCode(31916031L)).isEqualTo(18749137L);
        assertThat(nextCode(18749137L)).isEqualTo(16080970L);
        assertThat(nextCode(16080970L)).isEqualTo(21629792L);
    }
}