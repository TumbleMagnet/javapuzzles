package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WarehouseWoesTest {

    private static final List<String> TEST_INPUT = SolverUtils.readTest(WarehouseWoesTest.class);

    @Test
    void computeResultForPart1() {
        WarehouseWoes solver = new WarehouseWoes(TEST_INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(10092L);
    }

    @Test
    void computeResultForPart2Small() {

        List<String> smallInputPart2 = """
            #######
            #...#.#
            #.....#
            #..OO@#
            #..O..#
            #.....#
            #######
            
            <vv<<^^<<^^
            """.lines().toList();

        WarehouseWoes solver = new WarehouseWoes(smallInputPart2);
        solver.computeResultForPart2();
    }

    @Test
    void computeResultForPart2() {
        WarehouseWoes solver = new WarehouseWoes(TEST_INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(9021L);
    }
}