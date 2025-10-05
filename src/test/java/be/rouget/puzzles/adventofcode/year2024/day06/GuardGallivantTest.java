package be.rouget.puzzles.adventofcode.year2024.day06;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuardGallivantTest {

    private static final List<String> TEST_INPUT = SolverUtils.readTest(GuardGallivant.class);

    @Test
    void computeResultForPart1() {
        GuardGallivant solver = new GuardGallivant(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(41L);
    }

    @Test
    void computeResultForPart2() {
        GuardGallivant solver = new GuardGallivant(TEST_INPUT);
        solver.computeResultForPart1(); // Part 2 needs result from part 1
        assertThat(solver.computeResultForPart2()).isEqualTo(6L);
    }
}