package be.rouget.puzzles.adventofcode.year2022.day09;

import be.rouget.puzzles.adventofcode.util.map.Direction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Instruction(Direction direction, int distance) {

    public static Instruction parse(String input) {
        Pattern pattern = Pattern.compile("(.) (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        Direction direction = parseDirection(matcher.group(1));
        int distance = Integer.parseInt(matcher.group(2));
        return new Instruction(direction, distance);
    }

    private static Direction parseDirection(String input) {
        return switch (input) {
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            case "L" -> Direction.LEFT;
            case "R" -> Direction.RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction: " + input);
        };
    }
}
