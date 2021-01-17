package be.rouget.puzzles.adventofcode.year2020.day22;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Deck {

    private final String name;
    private final Deque<Long> cards = new ArrayDeque<>();

    public Deck(String name, List<Long> cardsTopToBottom) {
        this.name = name;
        for (Long card : cardsTopToBottom) {
            cards.addLast(card);
        }
    }

    public Long getTopCard() {
        return cards.removeFirst();
    }

    public void addCardToBottom(Long card) {
        cards.addLast(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        return name + ": " + StringUtils.join(cards, ",");
    }

    public Long computeScore() {
        long score = 0;
        long value = cards.size();
        for (Long card : cards) {
            score += card * value;
            value--;
        }
        return score;
    }

    public Deck copyDeck(int value) {
        List<Long> copyToBottom = Lists.newArrayList();
        Iterator<Long> iterator = cards.iterator();
        for (int i = 0; i < value; i++) {
            copyToBottom.add(iterator.next());
        }
        return new Deck(name, copyToBottom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return name.equals(deck.name) && Lists.newArrayList(cards).equals(Lists.newArrayList(deck.cards));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Lists.newArrayList(cards));
    }
}
