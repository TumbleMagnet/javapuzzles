package be.rouget.puzzles.adventofcode.year2020.day24;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HexDirection {

    WEST("w"),
    SOUTH_WEST("sw"),
    SOUTH_EAST("se"),
    EAST("e"),
    NORTH_EAST("ne"),
    NORTH_WEST("nw");

    private String mapCharacters;

    HexDirection(String mapCharacters) {
        this.mapCharacters = mapCharacters;
    }

    public static HexDirection fromMapCharacters(String input) {
        for (HexDirection direction : HexDirection.values()) {
            if (direction.getMapCharacters().equals(input)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("No direction matches input: " + input);
    }

    public String getMapCharacters() {
        return mapCharacters;
    }

    public static List<HexDirection> toDirections(String input) {
        List<HexDirection> directions = Lists.newArrayList();

        Pattern pattern = Pattern.compile("\\G(e|w|se|nw|sw|ne)");
        Matcher matcher = pattern.matcher(input);
        int lastMatchPos = 0;
        while (matcher.find()) {
            directions.add(HexDirection.fromMapCharacters(matcher.group(1)));
            lastMatchPos = matcher.end();
        }
        if (lastMatchPos != input.length()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        return directions;
    }
}
