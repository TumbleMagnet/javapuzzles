package be.rouget.puzzles.adventofcode.year2023.day10;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

import java.util.Arrays;
import java.util.Set;

import static be.rouget.puzzles.adventofcode.util.map.Direction.*;

public enum PipeTile implements MapCharacter {
    
    VERTICAL("|", Set.of(UP, DOWN)),
    HORIZONTAL("-", Set.of(LEFT, RIGHT)),
    NORTH_EAST("L", Set.of(UP, RIGHT)),
    NORTH_WEST("J", Set.of(UP, LEFT)),
    SOUTH_WEST("7", Set.of(DOWN, LEFT)),
    SOUTH_EAST("F", Set.of(DOWN, RIGHT)),
    GROUND(".", Set.of()),
    START("S", Set.of());
    
    private final String mapChar;
    private final Set<Direction> connections;

    PipeTile(String mapChar, Set<Direction> connections) {
        this.mapChar = mapChar;
        this.connections = connections;
    }

    @Override
    public String getMapChar() {
        return mapChar;
    }

    public Set<Direction> getConnections() {
        return connections;
    }
    
    public static PipeTile parse(String mapChar) {
        return Arrays.stream(PipeTile.values())
                .filter(t -> t.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find tile for character: " + mapChar));
    }
}
