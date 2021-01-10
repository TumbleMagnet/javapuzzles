package be.rouget.puzzles.adventofcode.year2020.day20;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class FinalImage extends RectangleMap<ImagePixel> {

    public FinalImage(int width, int height, Map<Position, ImagePixel> elements) {
        super(width, height, elements);
    }

    public static FinalImage fromSolution(TiledImage solution) {
        Map<Position, ImagePixel> elements = Maps.newHashMap();

        // Extract final image by removing the border of each title
        int reducedTileSize = ImageTile.TILE_SIZE - 2;
        for (int tileX = 0; tileX < solution.getEdgeSize(); tileX++) {
            for (int tileY = 0; tileY < solution.getEdgeSize(); tileY++) {
                ImageTile tile = solution.getTitleAtPosition(new Position(tileX, tileY));
                for (int x = 0; x < tile.getWidth(); x++) {
                    for (int y = 0; y < tile.getHeight(); y++) {
                        if (x > 0 && x < tile.getWidth() - 1 && y > 0 && y < tile.getHeight() - 1) {
                            Position newPosition = new Position(tileX * reducedTileSize + x - 1, tileY * reducedTileSize + y - 1);
                            ImagePixel pixel = tile.getElementAt(new Position(x, y));
                            elements.put(newPosition, pixel);
                        }
                    }
                }
            }
        }
        int imageSize = solution.getEdgeSize() * reducedTileSize;
        return  new FinalImage(imageSize, imageSize, elements);
    }

    public boolean hasSameBlackPixels(RectangleMap<ImagePixel> sprite, Position position) {
        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                ImagePixel spritePixel = sprite.getElementAt(new Position(x, y));
                if (spritePixel == ImagePixel.BLACK) {
                    ImagePixel imagePixel = getElementAt(new Position(position.getX() + x, position.getY() + y));
                    if (imagePixel != ImagePixel.BLACK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Set<Position> commonBlackPixels(RectangleMap<ImagePixel> sprite, Position position) {
        Set<Position> commonBlackPixels = Sets.newHashSet();
        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                ImagePixel spritePixel = sprite.getElementAt(new Position(x, y));
                if (spritePixel == ImagePixel.BLACK) {
                    Position imagePosition = new Position(position.getX() + x, position.getY() + y);
                    if (getElementAt(imagePosition) == ImagePixel.BLACK) {
                        commonBlackPixels.add(imagePosition);
                    }
                }
            }
        }
        return commonBlackPixels;
    }

    public FinalImage rotateRight() {
        Map<Position, ImagePixel> newElements = Maps.newHashMap();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                newElements.put(new Position(x, y), getElementAt(new Position(y, getWidth() - x - 1)));
            }
        }
        return new FinalImage(getWidth(), getHeight(), newElements);
    }

    public FinalImage flipVertically() {
        Map<Position, ImagePixel> newElements = Maps.newHashMap();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                newElements.put(new Position(x, y), getElementAt(new Position(getWidth() - x -1, y)));
            }
        }
        return new FinalImage(getWidth(), getHeight(), newElements);
    }

}
