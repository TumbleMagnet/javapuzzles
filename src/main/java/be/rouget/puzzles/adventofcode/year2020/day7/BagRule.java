package be.rouget.puzzles.adventofcode.year2020.day7;

import lombok.Data;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class BagRule {

    String bagColor;
    List<ColorCount> contents;

    public boolean contains(String color) {
        for (ColorCount content : contents) {
            if (content.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    public static BagRule fromInput(String line) {
        // posh purple bags contain 2 mirrored lavender bags, 4 light chartreuse bags, 3 shiny green bags.
        Pattern pattern = Pattern.compile("(.+) bags contain (.+)\\.");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Line does not match expected format: " + line);
        }
        return new BagRule(matcher.group(1), extractContents(matcher.group(2)));
    }

    private static List<ColorCount> extractContents(String input) {

        if (input.equals("no other bags")) {
            return List.of();
        }

        String[] contentItem = input.split(", ");
        return Arrays.stream(contentItem)
                .map(BagRule::parseColorCount)
                .collect(Collectors.toList());
    }

    private static ColorCount parseColorCount(String input) {
        Pattern pattern = Pattern.compile("(\\d) (.+) bag(s?)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Line does not match expected format: " + input);
        }
        int count = Integer.parseInt(matcher.group(1));
        String color = matcher.group(2);
        return new ColorCount(count, color);
    }

    @Value
    public static class ColorCount {
        int count;
        String color;
    }
}

