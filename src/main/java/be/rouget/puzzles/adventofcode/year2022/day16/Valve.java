package be.rouget.puzzles.adventofcode.year2022.day16;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Valve(String name, int flowRate, List<String> neighbours) {
    
    public static Valve parse(String input) {
        Pattern pattern = Pattern.compile("Valve (.+) has flow rate=(\\d+); tunnel(s*) lead(s)* to valve(s*) (.+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String name = matcher.group(1);
        int flowRate = Integer.parseInt(matcher.group(2));
        List<String> neighbours = Arrays.stream(matcher.group(6).split(", ")).toList();
        return new Valve(name, flowRate, neighbours);
    }
}
