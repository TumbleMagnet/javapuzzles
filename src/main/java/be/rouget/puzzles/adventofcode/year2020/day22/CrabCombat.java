package be.rouget.puzzles.adventofcode.year2020.day22;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class CrabCombat {

    private static final String YEAR = "2020";
    private static final String DAY = "22";

    private static final Logger LOG = LogManager.getLogger(CrabCombat.class);

    private final List<String> input;

    private Deck player1;
    private Deck player2;
    private int gameCount = -1;

    public CrabCombat(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    private void loadDecks() {
        List<String> inputWithBlankLine = Lists.newArrayList(input);
        inputWithBlankLine.add("");

        String currentName = null;
        List<Long> cards = Lists.newArrayList();
        for (String line : inputWithBlankLine) {
            if (line.startsWith("Player")) {
                currentName = line.replaceAll(":", "");
            } else if (StringUtils.isBlank(line)) {
                if (StringUtils.isBlank(currentName)) {
                    throw new IllegalStateException("No name for deck!");
                }
                if (currentName.endsWith("1")) {
                    player1 = new Deck(currentName, cards);
                    cards = Lists.newArrayList();
                } else {
                    player2 = new Deck(currentName, cards);
                }
            } else {
                cards.add(Long.valueOf(line));
            }
        }
        LOG.info("Starting deck {}", player1);
        LOG.info("Starting deck {}", player2);
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        CrabCombat aoc = new CrabCombat(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        loadDecks();

        int turn = 1;
        while (!player1.isEmpty() && !player2.isEmpty()) {
            Long card1 = player1.getTopCard();
            Long card2 = player2.getTopCard();
            if (card1 > card2) {
                LOG.info("Player1 wins turn {}!", turn);
                player1.addCardToBottom(card1);
                player1.addCardToBottom(card2);
            } else {
                LOG.info("Player2 wins turn {}", turn);
                player2.addCardToBottom(card2);
                player2.addCardToBottom(card1);
            }
            LOG.info("{}", player1);
            LOG.info("{}", player2);
            turn++;
        }
        if (player1.isEmpty()) {
            LOG.info("Player 2 wins the game!");
            LOG.info("Winning deck {}", player2);
            return player2.computeScore();
        } else if (player2.isEmpty()) {
            LOG.info("Player 1 wins the game!");
            LOG.info("Winning deck {}", player1);
            return player1.computeScore();
        }
        throw new IllegalStateException("No player won!");
    }

    public long computeResultForPart2() {
        loadDecks();
        this.gameCount = 0;
        int winner = playRecursiveCombat(player1, player2);
        if (winner == 2) {
            return player2.computeScore();
        } else if (winner == 1) {
            return player1.computeScore();
        }
        throw new IllegalStateException("No player won: return value " + winner);
    }

    private int playRecursiveCombat(Deck deck1, Deck deck2) {

        int turn = 1;
        this.gameCount++;
        int currentGameCount = gameCount;

        LOG.info("==== Starting Game {} ====", currentGameCount);
        LOG.info("Starting deck {}", deck1);
        LOG.info("Starting deck {}", deck2);

        Set<Deck> previousConfigurations1 = Sets.newHashSet();
        Set<Deck> previousConfigurations2 = Sets.newHashSet();

        while (!deck1.isEmpty() && !deck2.isEmpty()) {

            // If configuration of either player happened before => player 1 wins1
            if (previousConfigurations1.contains(deck1) || previousConfigurations2.contains(deck2)) {
                LOG.info("Detected similar configuration for either player in this game!");
                LOG.info("Player 1 wins game {}!", currentGameCount);
                LOG.info("==== End of game {} ===", currentGameCount);
                return 1;
            }
            previousConfigurations1.add(deck1.copyDeck(deck1.getSize()));
            previousConfigurations2.add(deck2.copyDeck(deck2.getSize()));

            Long card1 = deck1.getTopCard();
            boolean hasPlayer1EnoughCards = deck1.getSize() >= card1;
            Long card2 = deck2.getTopCard();
            boolean hasPlayer2EnoughCards = deck2.getSize() >= card2;
            int turnWinner;

            if (hasPlayer1EnoughCards && hasPlayer2EnoughCards) {
                // Start sub-game
                LOG.info("Starting sub-game...");
                Deck subDeck1 = deck1.copyDeck(Math.toIntExact(card1));
                Deck subDeck2 = deck2.copyDeck(Math.toIntExact(card2));
                turnWinner = playRecursiveCombat(subDeck1, subDeck2);
                LOG.info("Back to game {}...", currentGameCount);
            } else {
                // Player with highest card win the turn
                turnWinner = card1 > card2 ? 1 : 2;
            }
            if (turnWinner == 1) {
                LOG.info("Game {} - Player1 wins turn {}!", currentGameCount, turn);
                deck1.addCardToBottom(card1);
                deck1.addCardToBottom(card2);
            } else {
                LOG.info("Game {} - Player2 wins turn {}", currentGameCount, turn);
                deck2.addCardToBottom(card2);
                deck2.addCardToBottom(card1);
            }
            LOG.info("Game {} - {}", currentGameCount, deck1);
            LOG.info("Game {} - {}", currentGameCount, deck2);
            turn++;
        }

        if (deck1.isEmpty()) {
            LOG.info("Player 2 wins game {}!", currentGameCount);
            LOG.info("Winning deck {}", deck2);
            LOG.info("==== End of game {} ===", currentGameCount);
            return 2;
        } else if (deck2.isEmpty()) {
            LOG.info("Player 1 wins game {}!", currentGameCount);
            LOG.info("Winning deck {}", deck1);
            LOG.info("==== End of game {} ===", currentGameCount);
            return 1;
        }
        throw new IllegalStateException("No player won!");
    }
}