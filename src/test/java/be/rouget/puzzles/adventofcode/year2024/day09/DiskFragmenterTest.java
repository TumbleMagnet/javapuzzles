package be.rouget.puzzles.adventofcode.year2024.day09;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DiskFragmenterTest {
    private static final List<String> TEST_INPUT = SolverUtils.readTest(DiskFragmenter.class);

    @Test
    void computeResultForPart1() {
        DiskFragmenter solver = new DiskFragmenter(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(1928L);
    }

    @Test
    void computeResultForPart2() {
        DiskFragmenter solver = new DiskFragmenter(TEST_INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(2858L);
    }

    @Test
    void computeResultForPart2_02() {
        assertThat(new DiskFragmenter(List.of("1313165")).computeResultForPart2()).isEqualTo(169L);
    }
}