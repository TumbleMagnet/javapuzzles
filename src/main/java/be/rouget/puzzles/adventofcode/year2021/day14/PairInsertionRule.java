package be.rouget.puzzles.adventofcode.year2021.day14;

import lombok.Value;

@Value
public class PairInsertionRule {
    String pair;
    String result;
    String addedElement;


    public static PairInsertionRule parseRule(String input) {
        String[] parts = input.split(" -> ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        String pair = parts[0];
        if (pair.length() != 2) {
            throw new IllegalArgumentException("Invalid pair: " + pair);
        }
        String toInsert = parts[1];
        if (toInsert.length() != 1) {
            throw new IllegalArgumentException("Invalid char to insert: " + toInsert);
        }
        String result = pair.charAt(0) + toInsert + pair.charAt(1);
        return new PairInsertionRule(pair, result, toInsert);
    }
}
