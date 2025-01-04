package be.rouget.puzzles.adventofcode.year2023.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Part(int x, int m, int a, int s) {

    public static Part parse(String input) {
        // {x=787,m=2655,a=1222,s=2876}
        Pattern pattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int x = Integer.parseInt(matcher.group(1));
        int m = Integer.parseInt(matcher.group(2));
        int a = Integer.parseInt(matcher.group(3));
        int s = Integer.parseInt(matcher.group(4));
        return new Part(x, m, a, s);
    }
    
    public int computeRating() {
        return x + m + a +s;
    }

    public int getValue(String attributeName) {
        return switch (attributeName) {
            case "x" -> x();
            case "m" -> m();
            case "a" -> a();
            case "s" -> s();
            default -> throw new IllegalArgumentException("Invalid attribute: " + attributeName);
        };
    }
}
