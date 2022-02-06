package be.rouget.puzzles.adventofcode.year2021.day21;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class DiracDice {

    private static final Logger LOG = LogManager.getLogger(DiracDice.class);

    private static final int PLAYER1_START = 7;
    private static final int PLAYER2_START = 2;
    public static final int SCORE_TO_WIN_PART1 = 1000;
    public static final int SCORE_TO_WIN_PART2 = 21;

    private final int player1StartPosition;
    private final int player2StartPosition;

    private final Map<DiracDiceGameState, PlayerVictories> cache = Maps.newHashMap();

    public static void main(String[] args) {
        DiracDice aoc = new DiracDice(PLAYER1_START, PLAYER2_START);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public DiracDice(int player1StartPosition, int player2StartPosition) {
        this.player1StartPosition = player1StartPosition;
        this.player2StartPosition = player2StartPosition;
    }

    public long computeResultForPart1() {
        DiracDiceGameState gameState = initialGameState();
        DeterministicDice dice = new DeterministicDice();
        while (true) {
            int numberOfSpaces = dice.getNextValue() + dice.getNextValue() + dice.getNextValue();
            gameState = gameState.nextTurn(numberOfSpaces);
            Optional<PlayerVictories> optionalVictory = gameState.getOptionalVictory(SCORE_TO_WIN_PART1);
            if (optionalVictory.isPresent()) {
                PlayerState loser = getLoser(gameState, optionalVictory.get());
                return (long) loser.getScore() * (long) dice.getNumberOfRolls();
            }
        }
    }

    public long computeResultForPart2() {
        DiracDiceGameState diceGameState = initialGameState();
        PlayerVictories playerVictories = computeVictories(diceGameState);
        return Math.max(playerVictories.getPlayer1Victories(), playerVictories.getPlayer2Victories());
    }

    private DiracDiceGameState initialGameState() {
        PlayerState player1 = new PlayerState(player1StartPosition, 0);
        PlayerState player2 = new PlayerState(player2StartPosition, 0);
        return new DiracDiceGameState(player1, player2, 1);
    }

    public PlayerState getLoser(DiracDiceGameState gameState, PlayerVictories playerVictories) {
        if (playerVictories.getPlayer1Victories() == 1) {
            return gameState.getPlayer2();
        } else {
            return gameState.getPlayer1();
        }
    }

    private PlayerVictories computeVictories(DiracDiceGameState gameState) {

        // Try to get a hit from cache
        PlayerVictories cachedVictories = cache.get(gameState);
        if (cachedVictories != null) {
            return cachedVictories;
        }

        // If game is over, return victory
        Optional<PlayerVictories> optionalVictory = gameState.getOptionalVictory(SCORE_TO_WIN_PART2);
        if (optionalVictory.isPresent()) {
            PlayerVictories victories = optionalVictory.get();
            cache.put(gameState, victories);
            return victories;
        }

        // Otherwise, generate new game states by playing next move and compute total victories recursively
        PlayerVictories victories = new PlayerVictories(0, 0);
        for (DiracDiceGameState newGame : gameState.gamesAfterNextQuantumDiceRolls()) {
            PlayerVictories newVictories = computeVictories(newGame);
            victories = victories.add(newVictories);
        }
        cache.put(gameState, victories);
        return victories;
    }
}