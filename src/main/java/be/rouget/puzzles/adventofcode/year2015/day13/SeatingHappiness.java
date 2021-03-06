package be.rouget.puzzles.adventofcode.year2015.day13;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class SeatingHappiness {
    GuestPair guestPair;
    int happinessChange;

    public static SeatingHappiness fromInput(String input) {
        // Bob would gain 87 happiness units by sitting next to Mallory.
        // Carol would lose 16 happiness units by sitting next to George.
        Pattern pattern = Pattern.compile("(.*) would (gain|lose) (\\d*) happiness units by sitting next to (.*)\\.");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse: " + input);
        }
        String seater = matcher.group(1);
        String operator = matcher.group(2);
        int quantity = Integer.parseInt(matcher.group(3));
        String neighbour = matcher.group(4);
        return new SeatingHappiness(new GuestPair(seater, neighbour), "gain".equals(operator) ? quantity : -quantity);
    }
}
