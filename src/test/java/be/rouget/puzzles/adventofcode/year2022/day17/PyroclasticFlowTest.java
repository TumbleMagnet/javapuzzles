package be.rouget.puzzles.adventofcode.year2022.day17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PyroclasticFlowTest {

    public static final String TEST_INPUT = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";
    
    @Test
    void testPart1() {
        PyroclasticFlow solver = new PyroclasticFlow(List.of(TEST_INPUT));
        assertThat(solver.computeResultForPart1()).isEqualTo(3068L);
    }
}