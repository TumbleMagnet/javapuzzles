package be.rouget.puzzles.adventofcode.year2023.day07;

import java.util.Arrays;

public enum Card {
    JOKER("*"),
    TWO(  "2"),
    THREE("3"),
    FOUR( "4"),
    FIVE( "5"),
    SIX(  "6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE( "9"),
    TEN(  "T"),
    JACK( "J"),
    QUEEN("Q"),
    KING( "K"),
    AS(   "A"),
    ;
    
    private final String code;

    Card(String code) {
        this.code = code;
    }

    public static Card fromCode(String code) {
        return Arrays.stream(Card.values())
                .filter(card -> card.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No card found with code " + code));
    }

    public String getCode() {
        return code;
    }
}
