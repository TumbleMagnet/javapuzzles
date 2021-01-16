package be.rouget.puzzles.adventofcode.year2020.day21;

import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;

@Value
public class Food {

    private final static Pattern pattern = Pattern.compile("(.*) \\(contains (.*)\\)");

    List<String> ingredients;
    List<String> allergens;

    public static Food fromInput(String line) {

        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input line does not match pattern: " + line);
        }
        String[] ingredientArray = matcher.group(1).split(" ");
        String[] allergenArray = matcher.group(2).split(", ");

        return new Food(newArrayList(ingredientArray), newArrayList(allergenArray));
    }
}
