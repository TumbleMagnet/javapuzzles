package be.rouget.puzzles.adventofcode.year2016.day8;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class RotateColumnCommand implements ScreenCommand {

    Operations operation = Operations.ROTATE_COLUMN;
    int column;
    int shift;

    public static ScreenCommand parse(String input) {
        // rotate column x=12 by 5
        // rotate column x=column by shift
        Pattern pattern = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input does not match pattern: " + input);
        }
        int column = Integer.parseInt(matcher.group(1));
        int shift = Integer.parseInt(matcher.group(2));
        return new RotateColumnCommand(column, shift);
    }
}
