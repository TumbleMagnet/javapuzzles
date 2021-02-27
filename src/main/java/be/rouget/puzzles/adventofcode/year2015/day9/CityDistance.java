package be.rouget.puzzles.adventofcode.year2015.day9;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class CityDistance {
    String startCity;
    String endCity;
    int distance;

    public CityDistance reverse() {
        return new CityDistance(endCity, startCity, distance);
    }

    public static CityDistance fromInput(String line) {
        Pattern pattern = Pattern.compile("(.*) to (.*) = (.*)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + line);
        }
        String start = matcher.group(1);
        String end = matcher.group(2);
        int distance = Integer.parseInt(matcher.group(3));
        return new CityDistance(start, end, distance);
    }
}
