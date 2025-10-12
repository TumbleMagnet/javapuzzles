package be.rouget.puzzles.adventofcode.year2024.day07;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeRepairTest {

    private static final List<String> TEST_INPUT = SolverUtils.readTest(BridgeRepair.class);

    @Test
    void computeResultForPart1() {
        BridgeRepair solver = new BridgeRepair(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(3749L);
    }

    @Test
    void computeResultForPart2() {
        BridgeRepair solver = new BridgeRepair(TEST_INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(0L);
    }
}