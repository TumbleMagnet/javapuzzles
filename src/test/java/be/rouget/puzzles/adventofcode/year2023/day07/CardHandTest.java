package be.rouget.puzzles.adventofcode.year2023.day07;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardHandTest {

    @Test
    void computeType() {
        assertThat(cards("AAAAA").computeType()).isEqualTo(HandType.FIVE_OF_A_KIND);
        assertThat(cards("AA8AA").computeType()).isEqualTo(HandType.FOUR_OF_A_KIND);
        assertThat(cards("TTT98").computeType()).isEqualTo(HandType.THREE_OF_A_KIND);
        assertThat(cards("23432").computeType()).isEqualTo(HandType.TWO_PAIRS);
        assertThat(cards("A23A4").computeType()).isEqualTo(HandType.ONE_PAIR);
        assertThat(cards("23456").computeType()).isEqualTo(HandType.HIGH_CARD);
    }

    @Test
    void compareTo() {
        assertThat(cards("AAAAA")).isGreaterThan(cards("AA8AA"));
        assertThat(cards("AA8AA")).isGreaterThan(cards("TTT98"));
        assertThat(cards("TTT98")).isGreaterThan(cards("23432"));
        assertThat(cards("23432")).isGreaterThan(cards("A23A4"));
        assertThat(cards("A23A4")).isGreaterThan(cards("23456"));
        assertThat(cards("23456")).isLessThan(cards("A23A4"));
        
        assertThat(cards("KK677")).isGreaterThan(cards("KTJJT"));
        
    }
    
    private CardHand cards(String input) {
        return CardHand.parse(input + " 1");
    }
}