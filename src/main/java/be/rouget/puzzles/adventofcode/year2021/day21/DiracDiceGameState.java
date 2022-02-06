package be.rouget.puzzles.adventofcode.year2021.day21;

import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Value
public class DiracDiceGameState {

    PlayerState player1;
    PlayerState player2;
    int nextPlayerToPlay;

    private static final List<Integer> POSSIBLE_MOVES = computePossibleMoves();

    private static List<Integer> computePossibleMoves() {
        List<Integer> moves = Lists.newArrayList();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    moves.add(i + j + k);
                }
            }
        }
        return moves;
    }

    private int incrementNextPlayer() {
        if (nextPlayerToPlay == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    public List<DiracDiceGameState> gamesAfterNextQuantumDiceRolls() {
        List<DiracDiceGameState> newGames = Lists.newArrayList();
        for (Integer numberOfMoves : POSSIBLE_MOVES) {
            newGames.add(nextTurn(numberOfMoves));
        }
        return newGames;
    }

    public DiracDiceGameState nextTurn(Integer numberOfMoves) {
        PlayerState playerToMove = nextPlayerToPlay == 1 ? player1 : player2;
        PlayerState newPlayerState = playerToMove.move(newPosition(playerToMove.getPosition(), numberOfMoves));
        if (nextPlayerToPlay == 1) {
            return new DiracDiceGameState(newPlayerState, player2, incrementNextPlayer());
        } else {
            return new DiracDiceGameState(player1, newPlayerState, incrementNextPlayer());
        }
    }

    public static int newPosition(int currentPosition, int numberOfMoves) {
        int newPosition = (currentPosition + numberOfMoves) % 10;
        if (newPosition == 0) {
            newPosition = 10;
        }
        return newPosition;
    }

    public Optional<PlayerVictories> getOptionalVictory(int scoreToWin) {
        if (player1.getScore() >= scoreToWin) {
            return Optional.of(new PlayerVictories(1, 0));
        } else if (player2.getScore() >= scoreToWin) {
            return Optional.of(new PlayerVictories(0, 1));
        }
        // Game is not over yet
        return Optional.empty();
    }
}
