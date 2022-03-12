package be.rouget.puzzles.adventofcode.year2016.day19;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day19.AnElephantNamedJoseph.solvePart2;
import static be.rouget.puzzles.adventofcode.year2016.day19.AnElephantNamedJoseph.solvePart2Manually;
import static org.assertj.core.api.Assertions.assertThat;

class AnElephantNamedJosephTest {

    @Test
    void testSolvePart2Manually() {
        assertThat(solvePart2Manually(1)).isEqualTo(1);
        assertThat(solvePart2Manually(2)).isEqualTo(1);
        assertThat(solvePart2Manually(3)).isEqualTo(3);
        assertThat(solvePart2Manually(4)).isEqualTo(1);
        assertThat(solvePart2Manually(5)).isEqualTo(2);
        assertThat(solvePart2Manually(6)).isEqualTo(3);
        assertThat(solvePart2Manually(7)).isEqualTo(5);
        assertThat(solvePart2Manually(8)).isEqualTo(7);
        assertThat(solvePart2Manually(10)).isEqualTo(1);
        assertThat(solvePart2Manually(12)).isEqualTo(3);
    }

    @Test
    void testSolvePart2() {
        for (int i = 2; i < 100; i++) {
            assertThat(solvePart2(i)).as("Solve for " + i).isEqualTo(solvePart2Manually(i));
        }
    }
}