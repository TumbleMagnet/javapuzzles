package be.rouget.puzzles.adventofcode.year2021.day10;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static be.rouget.puzzles.adventofcode.util.AocStringUtils.extractCharacterList;

public class ProgramLine {

    private static final Map<String, String > pairs = Map.of(
            "(", ")",
            "[", "]",
            "{", "}",
            "<", ">"
    );

    private String firstInvalidChar = null;
    private String completion = null;

    public ProgramLine(String line) {
        Deque<String> parsingStack = new ArrayDeque<>();
        for (String c : extractCharacterList(line)) {
            if (isOpeningChar(c)) {
                parsingStack.addFirst(c);
            }
            else {
                String openingChar = parsingStack.removeFirst();
                if (!isMatchingPair(openingChar, c)) {
                    firstInvalidChar = c;
                    break;
                }
            }
        }
        if (firstInvalidChar == null) {
            StringBuilder builder = new StringBuilder();
            while (!parsingStack.isEmpty()) {
                builder.append(getMatchingClosingCharacter(parsingStack.removeFirst()));
            }
            completion = builder.toString();
        }
    }

    public boolean isCorrupted() {
        return firstInvalidChar != null;
    }

    public String getFirstInvalidChar() {
        return firstInvalidChar;
    }

    public String autoComplete() {
        if (isCorrupted()) {
            throw new IllegalStateException("Line is corrupted!");
        }
        return completion;
    }

    private boolean isOpeningChar(String s) {
        return pairs.containsKey(s);
    }

    private boolean isMatchingPair(String openingChar, String closingChar) {
        return getMatchingClosingCharacter(openingChar).equals(closingChar);
    }

    private String getMatchingClosingCharacter(String openingChar) {
        return pairs.get(openingChar);
    }
}
