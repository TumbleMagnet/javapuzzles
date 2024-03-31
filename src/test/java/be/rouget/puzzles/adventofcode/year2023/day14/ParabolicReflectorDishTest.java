package be.rouget.puzzles.adventofcode.year2023.day14;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParabolicReflectorDishTest {

    @Test
    void testExample() {
        String exampleInput = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....""";

        ParabolicReflectorDish solver = new ParabolicReflectorDish(AocStringUtils.readLines(exampleInput));
        assertThat(solver.computeResultForPart1()).isEqualTo(136L);
        assertThat(solver.computeResultForPart2()).isEqualTo(64L);
    }
}