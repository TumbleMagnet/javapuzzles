package be.rouget.puzzles.adventofcode.year2020.day24;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LobbyMap {

    private final Map<HexPosition, Tile> tiles = Maps.newHashMap();

    public LobbyMap() {
    }

    public LobbyMap(LobbyMap map) {
        for (Tile tile : map.tiles.values()) {
            Tile tileCopy = new Tile(tile.getPosition(), tile.isFlipped());
            this.tiles.put(tileCopy.getPosition(), tileCopy);
        }
    }

    public Tile getTile(HexPosition position) {
        Tile tile = tiles.get(position);
        if (tile == null) {
            tile = new Tile(position, false); // Not flipped by default
            tiles.put(position, tile);
        }
        return tile;
    }

    public long countFlippedTiles() {
        return tiles.values().stream().filter(Tile::isFlipped).count();
    }

    public LobbyMap addOneDay() {

        LobbyMap newMap = new LobbyMap(this);

        // Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
        List<HexPosition> blackPositions = tiles.values().stream()
                .filter(Tile::isFlipped)
                .map(Tile::getPosition)
                .collect(Collectors.toList()); // Intermediate collect as countFlippedNeighbours() will cause ConcurrentModificationException on "tiles"
        blackPositions.stream()
                .filter(position -> {
                    long flippedNeighbours = countFlippedNeighbours(position);
                    return flippedNeighbours == 0 || flippedNeighbours > 2;
                })
                .forEach(position -> newMap.getTile(position).flip());

        // Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
        // (limit to white tiles which are neighbours of black tiles and make sure each one is flipped only once)
        List<HexPosition> neighbourPositionsOfBlackTiles = tiles.values().stream()
                .filter(Tile::isFlipped)
                .map(Tile::getPosition)
                .flatMap(position -> position.getNeighbours().stream())
                .distinct()
                .collect(Collectors.toList()); // Intermediate collect as getTile() and countFlippedNeighbours() will cause ConcurrentModificationException on "tiles"
        neighbourPositionsOfBlackTiles.stream()
                .filter(position -> !getTile(position).isFlipped())
                .filter(position -> countFlippedNeighbours(position) == 2)
                .forEach(position -> newMap.getTile(position).flip());

        return newMap;
    }

    public long countFlippedNeighbours(HexPosition position) {
        return Arrays.stream(HexDirection.values())
                .map(position::move)
                .map(this::getTile)
                .filter(Tile::isFlipped)
                .count();
    }
}
