package be.rouget.puzzles.adventofcode.year2023.day11;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CosmicExpansionTest {

    public static final String EXAMPLE_INPUT = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....""";
    
    @Test
    void testPart1() {
        CosmicExpansion solver = new CosmicExpansion(AocStringUtils.readLines(EXAMPLE_INPUT));
        assertThat(solver.computeResultForPart1()).isEqualTo(374);
        assertThat(solver.expandUniverseAndMeasureDistances(10)).isEqualTo(1030);
        assertThat(solver.expandUniverseAndMeasureDistances(100)).isEqualTo(8410);
    }
}