package be.rouget.puzzles.adventofcode.year2023.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CamelCardsTest {

    public static final List<String> TEST_INPUT = List.of(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
    );
    
    @Test
    void testExample() {
        CamelCards camelCards = new CamelCards(TEST_INPUT);
        assertThat(camelCards.computeResultForPart1()).isEqualTo(6440L);
        assertThat(camelCards.computeResultForPart2()).isEqualTo(5905L);
    }
}