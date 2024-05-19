package be.rouget.puzzles.adventofcode.year2023.day16;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TheFloorWillBeLavaTest {

    @Test
    void testExample() {
        String exampleInput = """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....""";

        TheFloorWillBeLava solver = new TheFloorWillBeLava(AocStringUtils.readLines(exampleInput));
        assertThat(solver.computeResultForPart1()).isEqualTo(46L);
        assertThat(solver.computeResultForPart2()).isEqualTo(51L);
    }
}