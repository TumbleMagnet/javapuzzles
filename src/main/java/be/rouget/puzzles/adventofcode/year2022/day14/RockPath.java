package be.rouget.puzzles.adventofcode.year2022.day14;

import be.rouget.puzzles.adventofcode.util.map.Position;

import java.util.Arrays;
import java.util.List;

public record RockPath(List<Position> positions) {

    public static RockPath parse(String input) {
        String[] tokens = input.split(" -> ");
        List<Position> positions = Arrays.stream(tokens)
                .map(RockPath::parsePosition)
                .toList();
        return new RockPath(positions);
    }

    private static Position parsePosition(String input) {
        String[] tokens = input.split(",");
        int x = Integer.parseInt(tokens[0]);
        int y = Integer.parseInt(tokens[1]);
        return new Position(x, y);
    } 
}
