package be.rouget.puzzles.adventofcode.year2020.day22;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CrabCombatTest {

    private static final List<String> TEST_INPUT = List.of(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1",
            "",
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10"
    );


    @Test
    void computeResultForPart1() {
        CrabCombat crabCombat = new CrabCombat(TEST_INPUT);
        assertThat(crabCombat.computeResultForPart1()).isEqualTo(306L);
    }

    @Test
    void computeResultForPart2() {
        CrabCombat crabCombat = new CrabCombat(TEST_INPUT);
        assertThat(crabCombat.computeResultForPart2()).isEqualTo(291L);
    }

    @Test
    void preventInfiniteRecursion() {
        CrabCombat crabCombat = new CrabCombat(List.of(
                "Player 1:",
                "43",
                "19",
                "",
                "Player 2:",
                "2",
                "29",
                "14"
        ));
        crabCombat.computeResultForPart2();
    }

    @Test
    void testDeckEquals() {

        assertThat(new Deck("deck", List.of(1L, 2L, 3L))).isEqualTo(new Deck("deck", List.of(1L, 2L, 3L)));

        assertThat(new Deck("deck1", List.of(1L, 3L, 2L))).isNotEqualTo(new Deck("deck2", List.of(1L, 2L, 3L)));
        assertThat(new Deck("deck", List.of(1L, 3L, 2L))).isNotEqualTo(new Deck("deck", List.of(1L, 2L, 3L)));
        assertThat(new Deck("deck", List.of(1L, 2L, 5L))).isNotEqualTo(new Deck("deck", List.of(1L, 2L, 3L)));
    }
}