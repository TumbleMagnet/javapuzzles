package be.rouget.puzzles.adventofcode.year2023.day02;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Game(int index, List<Cubes> cubes) {

    public Game(int index, List<Cubes> cubes) {
        this.index = index;
        this.cubes = List.copyOf(cubes);
    }
    
    public boolean isCompatibleWithQuantities(Cubes quantities) {
        return cubes.stream()
                .allMatch(cubes -> cubes.isLessOrEqualTo(quantities));
    }

    public Cubes computeMinimumQuantities() {
        Cubes minimums = cubes.getFirst();
        for (Cubes draw : cubes) {
            minimums = minimums.max(draw);
        }
        return minimums;
    }
    
    public static Game parseGame(String input) {
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Pattern pattern = Pattern.compile("Game (\\d+): (.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int index = Integer.parseInt(matcher.group(1));
        String[] drawTokens = matcher.group(2).split("; ");
        List<Cubes> cubes = Arrays.stream(drawTokens)
                .map(Cubes::parse)
                .toList();

        return new Game(index, cubes);
    }
}
