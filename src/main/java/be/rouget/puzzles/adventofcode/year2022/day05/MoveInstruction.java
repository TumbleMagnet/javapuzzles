package be.rouget.puzzles.adventofcode.year2022.day05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record MoveInstruction(int quantity, int from, int to) {

    public static final Pattern PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    public static MoveInstruction parse(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int quantity = Integer.parseInt(matcher.group(1));
        int from = Integer.parseInt(matcher.group(2));
        int to = Integer.parseInt(matcher.group(3));
        return new MoveInstruction(quantity, from, to);
    }
}
