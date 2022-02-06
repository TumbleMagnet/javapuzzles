package be.rouget.puzzles.adventofcode.year2021.day21;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static be.rouget.puzzles.adventofcode.year2021.day21.DiracDiceGameState.newPosition;
import static org.assertj.core.api.Assertions.assertThat;

class DiracDiceGameStateTest {

    @Test
    void testNewPosition() {
        assertThat(newPosition(1, 1)).isEqualTo(2);
        assertThat(newPosition(1, 2)).isEqualTo(3);
        assertThat(newPosition(1, 3)).isEqualTo(4);
        assertThat(newPosition(1, 4)).isEqualTo(5);
        assertThat(newPosition(1, 5)).isEqualTo(6);
        assertThat(newPosition(1, 6)).isEqualTo(7);
        assertThat(newPosition(1, 7)).isEqualTo(8);
        assertThat(newPosition(1, 8)).isEqualTo(9);
        assertThat(newPosition(1, 9)).isEqualTo(10);
        assertThat(newPosition(1, 10)).isEqualTo(1);
        assertThat(newPosition(5, 10)).isEqualTo(5);
        assertThat(newPosition(5, 7)).isEqualTo(2);
    }

    @Test
    void testGetOptionalVictory() {
        assertThat(gameState(1, 21, 1, 20, 1).getOptionalVictory(21))
                .isEqualTo(Optional.of(new PlayerVictories(1, 0)));
        assertThat(gameState(1, 21, 1, 20, 2).getOptionalVictory(21))
                .isEqualTo(Optional.of(new PlayerVictories(1, 0)));
        assertThat(gameState(5, 21, 6, 20, 1).getOptionalVictory(21))
                .isEqualTo(Optional.of(new PlayerVictories(1, 0)));

        assertThat(gameState(5, 10, 6, 21, 1).getOptionalVictory(21))
                .isEqualTo(Optional.of(new PlayerVictories(0, 1)));
        assertThat(gameState(5, 20, 6, 25, 2).getOptionalVictory(21))
                .isEqualTo(Optional.of(new PlayerVictories(0, 1)));

        assertThat(gameState(5, 20, 6, 20, 2).getOptionalVictory(21))
                .isEqualTo(Optional.empty());
    }

    @Test
    void testNextTurn() {

        // Turn 1
        assertThat(gameState(4, 0, 8, 0, 1).gamesAfterNextQuantumDiceRolls()).containsExactlyInAnyOrder(
                gameState(7, 7, 8, 0, 2),
                gameState(8, 8, 8, 0, 2),
                gameState(8, 8, 8, 0, 2),
                gameState(8, 8, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(9, 9, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(10, 10, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(1, 1, 8, 0, 2),
                gameState(2, 2, 8, 0, 2),
                gameState(2, 2, 8, 0, 2),
                gameState(2, 2, 8, 0, 2),
                gameState(3, 3, 8, 0, 2)
        );

        // Turn 2
        assertThat(gameState(7, 7, 8, 0, 2).gamesAfterNextQuantumDiceRolls()).containsExactlyInAnyOrder(
                gameState(7, 7, 1, 1, 1),
                gameState(7, 7, 2, 2, 1),
                gameState(7, 7, 2, 2, 1),
                gameState(7, 7, 2, 2, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 3, 3, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 4, 4, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 5, 5, 1),
                gameState(7, 7, 6, 6, 1),
                gameState(7, 7, 6, 6, 1),
                gameState(7, 7, 6, 6, 1),
                gameState(7, 7, 7, 7, 1)
        );
    }

    private DiracDiceGameState gameState(int player1Position, int player1Score, int player2Position, int player2Score, int nextPlayer) {
        return new DiracDiceGameState(new PlayerState(player1Position, player1Score), new PlayerState(player2Position, player2Score), nextPlayer);
    }


}