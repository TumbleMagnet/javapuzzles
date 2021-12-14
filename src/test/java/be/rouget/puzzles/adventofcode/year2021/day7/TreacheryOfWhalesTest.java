package be.rouget.puzzles.adventofcode.year2021.day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TreacheryOfWhalesTest {

    private final TreacheryOfWhales solver = new TreacheryOfWhales(List.of("16,1,2,0,4,2,7,1,2,14"));

    @Test
    public void testExamples() {
        assertThat(solver.computeResultForPart1()).isEqualTo(37);
        assertThat(solver.computeResultForPart2()).isEqualTo(168);
    }

    @Test
    public void testCostFunctions() {
        assertThat(solver.costToMoveForPart2(16, 5)).isEqualTo(66);
    }

}