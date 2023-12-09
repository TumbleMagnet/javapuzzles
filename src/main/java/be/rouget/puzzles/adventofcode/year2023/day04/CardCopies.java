package be.rouget.puzzles.adventofcode.year2023.day04;

public record CardCopies(Card original, int quantity) {
    
    public static CardCopies fromOriginalCard(Card card) {
        return new CardCopies(card, 1);
    }

    public CardCopies addCopies(int numberOfCopies) {
        return new CardCopies(this.original, this.quantity + numberOfCopies);
    }
}
