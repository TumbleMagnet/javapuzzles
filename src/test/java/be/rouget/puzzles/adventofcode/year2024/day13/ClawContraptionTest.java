package be.rouget.puzzles.adventofcode.year2024.day13;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClawContraptionTest {
    private static final List<String> TEST_INPUT = SolverUtils.readTest(ClawContraption.class);

    @Test
    void computeResultForPart1() {
        ClawContraption solver = new ClawContraption(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(480L);
    }

}