package be.rouget.puzzles.adventofcode.year2015.day19;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Replacement {
    String input;
    String output;

    public static Replacement fromInputLine(String line) {
        Pattern pattern = Pattern.compile("(.*) => (.*)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse line: " + line);
        }
        String input = matcher.group(1);
        String ouput = matcher.group(2);
        return new Replacement(input, ouput);
    }
}
