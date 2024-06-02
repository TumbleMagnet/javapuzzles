package be.rouget.puzzles.adventofcode.year2023.day18;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LavaductLagoonTest {

    @Test
    void testExample() {
        String exampleInput = """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)""";

        LavaductLagoon solver = new LavaductLagoon(AocStringUtils.readLines(exampleInput));
        assertThat(solver.computeResultForPart1()).isEqualTo(62L);
        assertThat(solver.computeResultForPart2()).isEqualTo(952408144115L);
    }
}