package be.rouget.puzzles.adventofcode.util;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.util.AocMathUtils.lcm;
import static be.rouget.puzzles.adventofcode.util.AocMathUtils.lcmOfList;
import static org.assertj.core.api.Assertions.assertThat;

class AocMathUtilsTest {

    @Test
    void testLcm() {
        assertThat(lcm(2L, 3L)).isEqualTo(6L);
        assertThat(lcm(12L, 15L)).isEqualTo(60L);
        assertThat(lcmOfList(4L, 6L, 7L, 9L, 12L, 16L)).isEqualTo(1008L);
    }
}