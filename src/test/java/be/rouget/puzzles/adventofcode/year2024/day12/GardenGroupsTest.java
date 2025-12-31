package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GardenGroupsTest {
    private static final List<String> TEST_INPUT = SolverUtils.readTest(GardenGroups.class);

    @Test
    void computeResultForPart1And2() {
        GardenGroups solver = new GardenGroups(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(1930L);
        assertThat(solver.computeResultForPart2()).isEqualTo(1206L);
    }

    @Test
    void computeAnotherExample() {
        List<String> input = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
            """.lines().toList();
        GardenGroups solver = new GardenGroups(input);
        assertThat(solver.computeResultForPart2()).isEqualTo(368L);
    }
}