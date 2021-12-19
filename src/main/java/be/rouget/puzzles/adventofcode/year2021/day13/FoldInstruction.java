package be.rouget.puzzles.adventofcode.year2021.day13;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class FoldInstruction {

    public static final Pattern PATTERN = Pattern.compile("fold along ([xy])=(\\d+)");

    Orientation orientation;
    int coordinate;

    public static FoldInstruction parseInput(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid input: " + line);
        }
        String orientationString = matcher.group(1);
        int coordinate = Integer.parseInt(matcher.group(2));
        return new FoldInstruction(Orientation.parseInput(orientationString), coordinate);
    }
}
