package be.rouget.puzzles.adventofcode.year2023.day07;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void testOrdering() {
        assertThat(Card.TWO)
                .isEqualTo(Card.TWO)
                .isLessThan(Card.THREE);
        assertThat(Card.AS).isGreaterThan(Card.KING);
    }
}