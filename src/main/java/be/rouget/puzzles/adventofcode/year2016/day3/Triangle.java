package be.rouget.puzzles.adventofcode.year2016.day3;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Triangle {
    int length1;
    int length2;
    int length3;

    public boolean isValid() {
        return (length1 + length2 > length3) && (length1 + length3 > length2) && (length2 + length3 > length1);
    }

    public static Triangle fromInput(String line) {
        Pattern pattern = Pattern.compile("\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid triangle input: " + line);
        }
        int length1 = Integer.parseInt(matcher.group(1));
        int length2 = Integer.parseInt(matcher.group(2));
        int length3 = Integer.parseInt(matcher.group(3));
        return new Triangle(length1, length2, length3);
    }
}
