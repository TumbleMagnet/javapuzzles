package be.rouget.puzzles.adventofcode.year2024.day08;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResonantCollinearityTest {

    private static final List<String> TEST_INPUT = SolverUtils.readTest(ResonantCollinearity.class);

    @Test
    void computeResultForPart1() {
        ResonantCollinearity solver = new ResonantCollinearity(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(14L);
    }

    @Test
    void computeResultForPart2() {
        ResonantCollinearity solver = new ResonantCollinearity(TEST_INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(0L);
    }
}