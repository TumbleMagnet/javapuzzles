package be.rouget.puzzles.adventofcode.year2023.day06;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day06.WaitForIt.computeDistance;
import static org.assertj.core.api.Assertions.assertThat;

class WaitForItTest {

    @Test
    void testComputeDistance() {
        assertThat(computeDistance(7L, 0L)).isEqualTo(0L);
        assertThat(computeDistance(7L, 1L)).isEqualTo(6L);
        assertThat(computeDistance(7L, 2L)).isEqualTo(10L);
        assertThat(computeDistance(7L, 3L)).isEqualTo(12L);
        assertThat(computeDistance(7L, 4L)).isEqualTo(12L);
        assertThat(computeDistance(7L, 5L)).isEqualTo(10L);
        assertThat(computeDistance(7L, 5L)).isEqualTo(10L);
        assertThat(computeDistance(7L, 6L)).isEqualTo(6L);
        assertThat(computeDistance(7L, 7L)).isEqualTo(0L);
    }
}