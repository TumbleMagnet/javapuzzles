package be.rouget.puzzles.adventofcode.year2019;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day22.*;

class AoC2019Day22Test {

    @Test
    void testDealIntoNewStack() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_INTO_NEW_STACK, null));
        assertThat(deck.getCards()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    }

    private Deck createTestDeck() {
        return new Deck(10);
    }

    @Test
    void testPositiveCut() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.CUT, 3));
        assertThat(deck.getCards()).containsExactly(3, 4, 5, 6, 7, 8, 9, 0, 1, 2);
    }

    @Test
    void testNegativeCut() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.CUT, -4));
        assertThat(deck.getCards()).containsExactly(6, 7, 8, 9, 0, 1, 2, 3, 4, 5);
    }

    @Test
    void testDealWithIncrement() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_WITH_INCREMENT, 3));
        assertThat(deck.getCards()).containsExactly(0, 7, 4, 1, 8, 5, 2, 9, 6, 3);
    }

    @Test
    void testCase1() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_WITH_INCREMENT, 7));
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_INTO_NEW_STACK, null));
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_INTO_NEW_STACK, null));
        assertThat(deck.getCards()).containsExactly(0, 3, 6, 9, 2, 5, 8, 1, 4, 7);
    }

    @Test
    void testCase2() {
        Deck deck = createTestDeck();
        deck.shuffle(new ShuffleInstruction(ShuffleType.CUT, 6));
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_WITH_INCREMENT, 7));
        deck.shuffle(new ShuffleInstruction(ShuffleType.DEAL_INTO_NEW_STACK, null));
        assertThat(deck.getCards()).containsExactly(3, 0, 7, 4, 1, 8, 5, 2, 9, 6);
    }

    @Test
    void testCase3() {
        Deck deck = createTestDeck();
        String[] lines = new String[] {
                "deal with increment 7",
                "deal with increment 9",
                "cut -2"
        };
        Arrays.stream(lines).map(ShuffleInstruction::parse).forEach(i -> deck.shuffle(i));
        assertThat(deck.getCards()).containsExactly(6, 3, 0, 7, 4, 1, 8, 5, 2, 9);
    }

    @Test
    void testCase4() {
        Deck deck = createTestDeck();
        String[] lines = new String[] {
                "deal into new stack",
                "cut -2",
                "deal with increment 7",
                "cut 8",
                "cut -4",
                "deal with increment 7",
                "cut 3",
                "deal with increment 9",
                "deal with increment 3",
                "cut -1"
        };
        Arrays.stream(lines).map(ShuffleInstruction::parse).forEach(i -> deck.shuffle(i));
        assertThat(deck.getCards()).containsExactly(9, 2, 5, 8, 1, 4, 7, 0, 3, 6);
    }

}