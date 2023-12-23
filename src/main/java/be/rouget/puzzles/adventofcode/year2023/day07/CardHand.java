package be.rouget.puzzles.adventofcode.year2023.day07;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public record CardHand(List<Card> cards, long bid) implements Comparable<CardHand> {

    public HandType computeType() {
        if (!cards.contains(Card.JOKER)) {
            return computeTypeWithoutJoker();
        }
        
        // Check how many other different cards there are in the hand
        Set<Card> otherCardTypesThanJoker = cards.stream()
                .filter(card -> card != Card.JOKER)
                .collect(Collectors.toSet());
        if (otherCardTypesThanJoker.isEmpty()) {
            // When the hand contains only jokers, it is a "five of a kind"
            return HandType.FIVE_OF_A_KIND;
        }

        // When the hand contains jokers and other cards, we need to evaluate hands where the joker
        // is replaced with these other cards since:
        // - best hands are obtained when jokers are replaced with other cards from the hand
        // - best hands are obtained when we replace all jokers with the same card
        return otherCardTypesThanJoker.stream()
                .map(otherCardThanJoker -> this.replaceCard(Card.JOKER, otherCardThanJoker))
                .map(CardHand::computeType)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private HandType computeTypeWithoutJoker() {
        List<CardWihCount> sortedCardsWithCounts = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new CardWihCount(entry.getKey(), Math.toIntExact(entry.getValue())))
                .sorted(Comparator.comparing(CardWihCount::count).reversed())
                .toList();

        CardWihCount mostFrequentCard = sortedCardsWithCounts.get(0);
        if (mostFrequentCard.count() == 5L) {
            return HandType.FIVE_OF_A_KIND;
        } else if (mostFrequentCard.count() == 4L) {
            return HandType.FOUR_OF_A_KIND;
        }

        CardWihCount secondMostFrequentCard = sortedCardsWithCounts.get(1);
        if (mostFrequentCard.count() == 3L) {
            if (secondMostFrequentCard.count() == 2L) {
                return HandType.FULL_HOUSE;
            } else {
                return HandType.THREE_OF_A_KIND;
            }
        }

        if (mostFrequentCard.count() == 2L) {
            if (secondMostFrequentCard.count() == 2L) {
                return HandType.TWO_PAIRS;
            } else {
                return HandType.ONE_PAIR;
            }
        }

        return HandType.HIGH_CARD;
    }

    @Override
    public int compareTo(CardHand other) {
        int typeComparison = this.computeType().compareTo(other.computeType());
        if (typeComparison != 0) {
            return typeComparison;
        }
        for (int i = 0; i < cards.size(); i++) {
            int cardComparison = this.cards.get(i).compareTo(other.cards.get(i));
            if (cardComparison != 0) {
                return cardComparison;
            }
        }
        return 0;
    }

    public CardHand replaceCard(Card from, Card to) {
        List<Card> newCards = cards.stream()
                .map(card -> card == from ? to : card)
                .toList();
        return new CardHand(newCards, bid);
    }

    public static CardHand parse(String input) {
        // JTTT2 186
        String[] tokens = input.split(" ");
        List<Card> cards = AocStringUtils.extractCharacterList(tokens[0]).stream()
                .map(Card::fromCode)
                .toList();
        if (cards.size() != 5) {
            throw new IllegalArgumentException("Found " + cards.size() + " cards in hand " + input);
        }
        long bid = Long.parseLong(tokens[1]);
        return new CardHand(cards, bid);
    }
}
