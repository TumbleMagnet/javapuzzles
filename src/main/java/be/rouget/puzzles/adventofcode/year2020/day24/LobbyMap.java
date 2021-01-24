package be.rouget.puzzles.adventofcode.year2020.day24;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

public class LobbyMap {

    private final Set<HexPosition> flippedTiles = Sets.newHashSet();

    public LobbyMap() {
    }

    public LobbyMap(LobbyMap map) {
        flippedTiles.addAll(map.flippedTiles);
    }

    public boolean isFlipped(HexPosition position) {
        return flippedTiles.contains(position);
    }

    public void flip(HexPosition position) {
        if (isFlipped(position)) {
            flippedTiles.remove(position);
        } else {
            flippedTiles.add(position);
        }
    }

    public long countFlippedTiles() {
        return flippedTiles.size();
    }

    public LobbyMap addOneDay() {

        LobbyMap newMap = new LobbyMap(this);

        // Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
        flippedTiles.stream()
                .filter(position -> {
                    long flippedNeighbours = countFlippedNeighbours(position);
                    return flippedNeighbours == 0 || flippedNeighbours > 2;
                })
                .forEach(newMap::flip);

        // Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
        // (limit to white tiles which are neighbours of black tiles and make sure each one is flipped only once)
        flippedTiles.stream()
                .flatMap(position -> position.getNeighbours().stream())
                .distinct()
                .filter(position -> !isFlipped(position))
                .filter(position -> countFlippedNeighbours(position) == 2)
                .forEach(newMap::flip);

        return newMap;
    }

    public long countFlippedNeighbours(HexPosition position) {
        return Arrays.stream(HexDirection.values())
                .map(position::move)
                .filter(this::isFlipped)
                .count();
    }
}
