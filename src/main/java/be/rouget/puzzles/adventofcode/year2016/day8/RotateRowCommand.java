package be.rouget.puzzles.adventofcode.year2016.day8;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class RotateRowCommand implements ScreenCommand {

    Operations operation = Operations.ROTATE_ROW;
    int row;
    int shift;

    public static ScreenCommand parse(String input) {
        // rotate row y=13 by 4
        // rotate row y=row by shift
        Pattern pattern = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input does not match pattern: " + input);
        }
        int row = Integer.parseInt(matcher.group(1));
        int shift = Integer.parseInt(matcher.group(2));
        return new RotateRowCommand(row, shift);
    }
}
