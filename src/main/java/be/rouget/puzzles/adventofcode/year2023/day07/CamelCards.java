package be.rouget.puzzles.adventofcode.year2023.day07;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.IntStream;


public class CamelCards {

    private static final Logger LOG = LogManager.getLogger(CamelCards.class);
    private final List<CardHand> hands;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CamelCards.class);
        CamelCards aoc = new CamelCards(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CamelCards(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        hands = input.stream()
                .map(CardHand::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return computeWinnings(hands);
    }

    public long computeResultForPart2() {
        List<CardHand> handsWithJokers = hands.stream()
                .map(hand -> hand.replaceCard(Card.JACK, Card.JOKER))
                .toList();
        return computeWinnings(handsWithJokers);
    }

    private static long computeWinnings(List<CardHand> inputHands) {
        List<CardHand> sortedHands = inputHands.stream()
                .sorted()
                .toList();

        return IntStream.rangeClosed(0, sortedHands.size() - 1)
                .mapToLong(i -> (i + 1) * sortedHands.get(i).bid())
                .sum();
    }
}