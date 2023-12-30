package be.rouget.puzzles.adventofcode.year2023.day08;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Node(String name, String left, String right) {

    public static Node parse(String input) {
        // DRM = (DLQ, BGR)
        Pattern pattern = Pattern.compile("(.*) = \\((.*), (.*)\\)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String name = matcher.group(1);
        String left = matcher.group(2);
        String right = matcher.group(3);
        return new Node(name, left, right);
    }
}
