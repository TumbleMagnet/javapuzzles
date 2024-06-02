package be.rouget.puzzles.adventofcode.year2023.day18;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day18.DigInstruction.parseForPart1;
import static be.rouget.puzzles.adventofcode.year2023.day18.DigInstruction.parseForPart2;
import static org.assertj.core.api.Assertions.assertThat;

class DigInstructionTest {

    @Test
    void testParseForPart1() {
        assertThat(parseForPart1("R 6 (#70c710)")).isEqualTo(new DigInstruction(Direction.RIGHT, 6L));
    }

    @Test
    void testParseForPart2() {
        assertThat(parseForPart2("X 0 (#70c710)")).isEqualTo(new DigInstruction(Direction.RIGHT, 461937L));
        assertThat(parseForPart2("X 0 (#0dc571)")).isEqualTo(new DigInstruction(Direction.DOWN, 56407L));
        assertThat(parseForPart2("X 0 (#8ceee2)")).isEqualTo(new DigInstruction(Direction.LEFT, 577262L));
        assertThat(parseForPart2("X 0 (#caa173)")).isEqualTo(new DigInstruction(Direction.UP, 829975L));
    }
}