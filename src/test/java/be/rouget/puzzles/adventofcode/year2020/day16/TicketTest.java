package be.rouget.puzzles.adventofcode.year2020.day16;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void fromInput() {
        assertThat(Ticket.fromInput("73,53,173,107,113,89,59,167,137,139,71,179,131,181,67,83,109,127,61,79").getValues())
                    .containsExactly(73,53,173,107,113,89,59,167,137,139,71,179,131,181,67,83,109,127,61,79);
    }
}