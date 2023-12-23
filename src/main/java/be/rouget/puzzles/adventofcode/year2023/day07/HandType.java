package be.rouget.puzzles.adventofcode.year2023.day07;

public enum HandType {

    // High card, where all cards' labels are distinct: 23456
    HIGH_CARD,
    
    // One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
    ONE_PAIR,
    
    // Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
    TWO_PAIRS,
    
    // Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
    THREE_OF_A_KIND,
    
    // Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
    FULL_HOUSE,
    
    // Four of a kind, where four cards have the same label and one card has a different label: AA8AA
    FOUR_OF_A_KIND,
    
    // Five of a kind, where all five cards have the same label: AAAAA
    FIVE_OF_A_KIND
}
