package be.rouget.puzzles.adventofcode.year2016.day8;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class RectangleCommand implements ScreenCommand {

    Operations operation = Operations.RECTANGLE;
    int width;
    int height;

    public static ScreenCommand parse(String input) {
        // rect 1x2
        // rect width x height
        Pattern pattern = Pattern.compile("rect (\\d+)x(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input does not match pattern: " + input);
        }
        int width = Integer.parseInt(matcher.group(1));
        int height = Integer.parseInt(matcher.group(2));
        return new RectangleCommand(width, height);
    }
}
