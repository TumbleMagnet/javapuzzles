package be.rouget.puzzles.adventofcode.year2016.day8;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day8.TwoFactorAuthentication.parseCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TwoFactorAuthenticationTest {

    @Test
    void testParseCommand() {
        assertThat(parseCommand("rect 2x3")).isEqualTo(new RectangleCommand(2,3));
        assertThat(parseCommand("rotate row y=13 by 4")).isEqualTo(new RotateRowCommand(13,4));
        assertThat(parseCommand("rotate column x=30 by 1")).isEqualTo(new RotateColumnCommand(30,1));
    }
}