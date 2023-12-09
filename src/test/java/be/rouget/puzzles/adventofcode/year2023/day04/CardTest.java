package be.rouget.puzzles.adventofcode.year2023.day04;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

    @Test
    void testParse() {

        Card actual = Card.parse("Card   1: 33 34 29 52 91  7 31 42  2  6 | 53 52  6 96 42 91  2 23  7 38 90 28 31 51  1 26 33 22 95 34 29 77 32 86  3");
        assertThat(actual).isEqualTo(new Card(1,
                List.of(33, 34, 29, 52, 91, 7, 31, 42, 2, 6),
                List.of(53, 52, 6, 96, 42, 91, 2, 23, 7, 38, 90, 28, 31, 51, 1, 26, 33, 22, 95, 34, 29, 77, 32, 86, 3)));

    }

    @Test
    void testExtractWinningNumbersOnCard() {
        Card card = Card.parse("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        assertThat(card.extractWinningNumbersOnCard()).containsExactlyInAnyOrder(48, 83, 17, 86);
    }
    
    @Test
    void testComputePoints() {
        Card card = Card.parse("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        assertThat(card.computePoints()).isEqualTo(8);        
    }
}    
    