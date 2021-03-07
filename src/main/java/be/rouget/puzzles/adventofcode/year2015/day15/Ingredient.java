package be.rouget.puzzles.adventofcode.year2015.day15;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Ingredient {
    String name;
    int capacity;
    int durability;
    int flavor;
    int texture;
    int calories;

    public static Ingredient fromInput(String input) {

        // Sugar: capacity 3, durability 0, flavor 0, texture -3, calories 2
        Pattern pattern = Pattern.compile("(.*): capacity (.*), durability (.*), flavor (.*), texture (.*), calories (.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse: " + input);
        }
        String name = matcher.group(1);
        int capacity = Integer.parseInt(matcher.group(2));
        int durability = Integer.parseInt(matcher.group(3));
        int flavor = Integer.parseInt(matcher.group(4));
        int texture = Integer.parseInt(matcher.group(5));
        int calories = Integer.parseInt(matcher.group(6));
        return new Ingredient(name, capacity, durability, flavor, texture, calories);
    }

}
