package be.rouget.puzzles.adventofcode.year2024.day10;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HoofItTest {
    private static final List<String> TEST_INPUT = SolverUtils.readTest(HoofIt.class);

    @Test
    void computeResultForPart1() {
        HoofIt solver = new HoofIt(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(36L);
    }

    @Test
    void computeResultForPart2() {
        HoofIt solver = new HoofIt(TEST_INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(81L);
    }
}