package be.rouget.puzzles.adventofcode.year2016.day1;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Instruction {
    Rotation rotation;
    int distance;

    public static Instruction fromInput(String input) {

        Pattern pattern = Pattern.compile("([RL])(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        Rotation rotation = letterToRotation(matcher.group(1));
        int distance = Integer.parseInt(matcher.group(2));
        return new Instruction(rotation, distance);
    }

    private static Rotation letterToRotation(String rotationLetter) {
        if ("R".equals(rotationLetter)) {
            return Rotation.RIGHT;
        } else if ("L".equals(rotationLetter)) {
            return Rotation.LEFT;
        }
        throw new IllegalArgumentException("Invalid rotation letter: " + rotationLetter);
    }
}
