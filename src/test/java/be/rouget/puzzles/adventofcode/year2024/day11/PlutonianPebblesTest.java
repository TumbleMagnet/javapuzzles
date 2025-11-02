package be.rouget.puzzles.adventofcode.year2024.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2024.day11.PlutonianPebbles.blinkStone;
import static org.assertj.core.api.Assertions.assertThat;

class PlutonianPebblesTest {

    @Test
    void testBlinkStone() {
        // Rule 1
        assertThat(blinkStone(0L)).containsExactly(1L);

        // Rule 2
        assertThat(blinkStone(10L)).containsExactly(1L, 0L);
        assertThat(blinkStone(99L)).containsExactly(9L, 9L);
        assertThat(blinkStone(1000L)).containsExactly(10L, 0L);

        // Rule 3
        assertThat(blinkStone(1L)).containsExactly(2024L);
        assertThat(blinkStone(999L)).containsExactly(2021976L);
    }

    @Test
    void computeResultForPart1() {
        PlutonianPebbles solver = new PlutonianPebbles(List.of("125 17"));
        assertThat(solver.computeResultForPart1()).isEqualTo(55312L);
    }
}