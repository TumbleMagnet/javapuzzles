package be.rouget.puzzles.adventofcode.year2023.day18;

import be.rouget.puzzles.adventofcode.util.map.Direction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record DigInstruction(Direction direction, long distance) {

    public static final Pattern PATTERN = Pattern.compile("(.*) (\\d+) \\((.*)\\)");

    public static DigInstruction parseForPart1(String input) {
        // R 6 (#70c710)
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        Direction direction = parseDirectionFromLetter(matcher.group(1));
        long distance = Long.parseLong(matcher.group(2));
        return new DigInstruction(direction, distance);
    }

    private static Direction parseDirectionFromLetter(String input) {
        return switch (input) {
            case "R" -> Direction.RIGHT;
            case "L" -> Direction.LEFT;
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            default -> throw new IllegalArgumentException("Unknown direction: " + input);
        };
    }

    private static Direction parseDirectionFromDigit(String input) {
        return switch (input) {
            case "0" -> Direction.RIGHT;
            case "1" -> Direction.DOWN;
            case "2" -> Direction.LEFT;
            case "3" -> Direction.UP;
            default -> throw new IllegalArgumentException("Unknown direction: " + input);
        };
    }

    public static DigInstruction parseForPart2(String input) {
        // R 6 (#70c710)
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String hexValue = matcher.group(3);
        if (hexValue.length() != 7) {
            throw new IllegalArgumentException("Cannot parse hex value: " + hexValue);
        }
        Direction direction = parseDirectionFromDigit(hexValue.substring(6));
        long distance = Long.decode(hexValue.substring(0, 6));
        return new DigInstruction(direction, distance);
    }

}
