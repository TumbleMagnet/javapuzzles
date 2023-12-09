package be.rouget.puzzles.adventofcode.year2023.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Cubes(int red, int green, int blue) {

    public Cubes add(Cubes other) {
        return new Cubes(this.red + other.red, this.green + other.green, this.blue + other.blue);
    }
    
    public boolean isLessOrEqualTo(Cubes other) {
        return this.red <= other.red && this.green <= other.green && this.blue <= other.blue;
    }
    
    public Cubes max(Cubes other) {
        return new Cubes(
                Math.max(this.red,  other.red),
                Math.max(this.green,  other.green),
                Math.max(this.blue,  other.blue)
        );
    }
    
    public int computePower() {
        return this.red * this.green * this.blue;
    }

    public static Cubes parse(String input) {
        // 1 green, 3 red, 6 blue
        // 6 red, 1 blue
        // 2 green
        String[] tokens = input.split(", ");
        Cubes cubes = new Cubes(0, 0, 0);
        for (String token : tokens) {
            cubes = cubes.add(parseOneColorDraw(token));
        }
        return cubes;
    }

    private static Cubes parseOneColorDraw(String oneColorDraw) {
        Pattern pattern = Pattern.compile("(\\d+) (.*)");
        Matcher matcher = pattern.matcher(oneColorDraw);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + oneColorDraw);
        }
        int quantity = Integer.parseInt(matcher.group(1));
        CubeColor color = CubeColor.valueOf(matcher.group(2).toUpperCase());
        return fromOneColor(quantity, color);
    }

    private static Cubes fromOneColor(int quantity, CubeColor color) {
        return switch (color) {
            case RED -> new Cubes(quantity, 0, 0);
            case GREEN -> new Cubes(0, quantity, 0);
            case BLUE -> new Cubes(0, 0, quantity);
        };
    }
}
