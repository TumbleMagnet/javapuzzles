package be.rouget.puzzles.adventofcode.year2020.day16;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class FieldRule {
    String name;
    Range range1;
    Range range2;

    public static FieldRule fromInput(String line) {
        // departure location: 27-672 or 680-954
        Pattern pattern = Pattern.compile("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input does not match expected format: " + line);
        }
        String name = matcher.group(1);
        int r1low = Integer.parseInt(matcher.group(2));
        int r1high = Integer.parseInt(matcher.group(3));
        int r2low = Integer.parseInt(matcher.group(4));
        int r2high = Integer.parseInt(matcher.group(5));
        return new FieldRule(name, new Range(r1low, r1high), new Range(r2low, r2high));
    }

    public boolean matches(int value) {
        return range1.contains(value) || range2.contains(value);
    }

}
