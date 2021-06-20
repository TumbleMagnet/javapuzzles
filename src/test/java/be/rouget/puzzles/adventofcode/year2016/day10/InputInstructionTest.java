package be.rouget.puzzles.adventofcode.year2016.day10;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day10.InputInstruction.parseFromInput;
import static org.assertj.core.api.Assertions.assertThat;

class InputInstructionTest {

    @Test
    void testParseFromInput() {
        assertThat(parseFromInput("value 23 goes to bot 208")).isEqualTo(new InputInstruction(23, 208));
        assertThat(parseFromInput("bot 125 gives low to bot 58 and high to bot 57")).isNull();
    }
}