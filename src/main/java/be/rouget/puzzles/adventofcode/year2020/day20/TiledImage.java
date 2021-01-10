package be.rouget.puzzles.adventofcode.year2020.day20;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TiledImage {

    private final static int IMAGE_SIZE = 12;

    private Map<Position, ImageTile> tiles;

    public TiledImage() {
        tiles = new HashMap<>();
    }

    public TiledImage(Map<Position, ImageTile> tiles) {
        this.tiles = tiles;
    }

    public boolean fits(ImageTile newTile) {

        Position nextFreePosition = getNextFreePosition();

        // Check tile on the left
        if (nextFreePosition.getX() > 0) {
            ImageTile leftTile = tiles.get(new Position(nextFreePosition.getX() - 1, nextFreePosition.getY()));
            if (!matchesHorizontally(leftTile, newTile)) {
                return false;
            }
        }

        // Check upper tile
        if (nextFreePosition.getY() > 0) {
            ImageTile upperTile = tiles.get(new Position(nextFreePosition.getX(), nextFreePosition.getY() - 1));
            if (!matchesVertically(upperTile, newTile)) {
                return false;
            }
        }

        // No need to check tile on the right because we add tiles from left to right
        // No need to check lower tile because we add tiles from top to bottom
        return true;
    }

    public static boolean matchesHorizontally(ImageTile leftTile, ImageTile rightTitle) {
        return Arrays.equals(leftTile.getRightColumn(), rightTitle.getLeftColumn());
    }

    public static boolean matchesVertically(ImageTile topTile, ImageTile bottomTitle) {
        return Arrays.equals(topTile.getBottomRow(), bottomTitle.getTopRow());
    }

    public TiledImage addTile(ImageTile newTile) {
        Map<Position, ImageTile> newTiles = Maps.newHashMap(tiles);
        Position nextFreePosition = getNextFreePosition();
        newTiles.put(nextFreePosition, newTile);
        return new TiledImage(newTiles);
    }

    public boolean isFull() {
        return getNumberOfTiles() == IMAGE_SIZE * IMAGE_SIZE;
    }

    public int getNumberOfTiles() {
        return tiles.entrySet().size();
    }

    private Position getNextFreePosition() {
        if (isFull()) {
            throw new IllegalStateException("Map is full");
        }
        int currentSize = getNumberOfTiles();
        int nextX = currentSize % IMAGE_SIZE;
        int nextY = currentSize / IMAGE_SIZE;
        return new Position(nextX, nextY);
    }

    public ImageTile getTitleAtPosition(Position position) {
        return tiles.get(position);
    }

    public int getEdgeSize() {
        return IMAGE_SIZE;
    }
}
