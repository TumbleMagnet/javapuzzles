package be.rouget.puzzles.adventofcode.year2016.day10;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day10.BotInstruction.parseFromInput;
import static be.rouget.puzzles.adventofcode.year2016.day10.DestinationType.BOT;
import static be.rouget.puzzles.adventofcode.year2016.day10.DestinationType.OUTPUT;
import static org.assertj.core.api.Assertions.assertThat;

class BotInstructionTest {

    @Test
    void testParseFromInput() {
        assertThat(parseFromInput("bot 131 gives low to output 6 and high to bot 151")).isEqualTo(
                new BotInstruction(131, new Destination(OUTPUT, 6), new Destination(BOT, 151)));
        assertThat(parseFromInput("bot 33 gives low to bot 13 and high to output 1")).isEqualTo(
                new BotInstruction(33, new Destination(BOT, 13), new Destination(OUTPUT, 1)));
        assertThat(parseFromInput("value 23 goes to bot 208")).isNull();
    }
}