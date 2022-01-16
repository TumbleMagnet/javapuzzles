package be.rouget.puzzles.adventofcode.year2021.day20;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrenchMapTest {

    @Test
    void solveTestInput() {
        TrenchMap trenchMap = new TrenchMap(SolverUtils.readTest(TrenchMap.class));
        assertThat(trenchMap.computeResultForPart1()).isEqualTo(35);
    }
}