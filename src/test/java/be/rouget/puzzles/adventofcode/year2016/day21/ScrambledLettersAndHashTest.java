package be.rouget.puzzles.adventofcode.year2016.day21;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2016.day21.ScrambledLettersAndHash.parse;
import static org.assertj.core.api.Assertions.assertThat;

class ScrambledLettersAndHashTest {

    @Test
    void testParse() {
        assertThat(parse("swap position 6 with position 0")).isEqualTo(new SwapPosition(6, 0));
        assertThat(parse("swap letter d with letter c")).isEqualTo(new SwapLetter("d", "c"));
        assertThat(parse("rotate left 4 steps")).isEqualTo(new Rotate(Direction.LEFT, 4));
        assertThat(parse("rotate right 1 step")).isEqualTo(new Rotate(Direction.RIGHT, 1));
        assertThat(parse("rotate based on position of letter g")).isEqualTo(new RotateBasedOnLetter("g"));
        assertThat(parse("reverse positions 2 through 4")).isEqualTo(new Reverse(2, 4));
        assertThat(parse("move position 7 to position 5")).isEqualTo(new Move(7, 5));
    }

    @Test
    void testParts() {
        ScrambledLettersAndHash scrambler = new ScrambledLettersAndHash(List.of(
                "swap position 4 with position 0",
                "swap letter d with letter b",
                "reverse positions 0 through 4",
                "rotate left 1 step",
                "move position 1 to position 4",
                "move position 3 to position 0",
                "rotate based on position of letter b",
                "rotate based on position of letter d"
        ));
        assertThat(scrambler.computeResultForPart1("abcde")).isEqualTo("decab");
        assertThat(scrambler.computeResultForPart2("decab")).isEqualTo("abcde");
    }
}