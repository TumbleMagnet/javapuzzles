package be.rouget.puzzles.adventofcode.year2020.day20;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ImageTile extends RectangleMap<ImagePixel> {

    public static final int TILE_SIZE = 10;
    private static final Logger LOG = LogManager.getLogger(JurassicJigsaw.class);

    private long id;

    public ImageTile(long id, List<String> input) {
        super(input, ImagePixel::fromMapChar);
        this.id = id;
        if ((getHeight() != TILE_SIZE) || (getWidth() != TILE_SIZE)) {
            LOG.error("Tile {} does not have expected dimensions: {}x{}", getId(), getWidth(), getHeight());
            throw new IllegalStateException("Invalid dimensions!");
        }
    }

    public ImageTile(int width, int height, Map<Position, ImagePixel> elements, long id) {
        super(width, height, elements);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public ImagePixel[] getLeftColumn() {
        return getColumn(0);
    }

    public ImagePixel[] getRightColumn() {
        return getColumn(getWidth() - 1);
    }

    public ImagePixel[] getColumn(int x) {
        ImagePixel[] column = new ImagePixel[getHeight()];
        for (int y = 0; y < getHeight(); y++) {
            column[y] = getElementAt(new Position(x, y));
        }
        return column;
    }

    public ImagePixel[] getTopRow() {
        return getRow(0);
    }

    public ImagePixel[] getBottomRow() {
        return getRow(getHeight() - 1);
    }

    public ImagePixel[] getRow(int y) {
        ImagePixel[] row = new ImagePixel[getWidth()];
        for (int x = 0; x < getWidth(); x++) {
            row[x] = getElementAt(new Position(x, y));
        }
        return row;
    }

    public ImageTile rotateRight() {
        Map<Position, ImagePixel> newElements = Maps.newHashMap();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                newElements.put(new Position(x, y), getElementAt(new Position(y, TILE_SIZE - x - 1)));
            }
        }
        return new ImageTile(getWidth(), getHeight(), newElements, getId());
    }

    public ImageTile flipVertically() {
        Map<Position, ImagePixel> newElements = Maps.newHashMap();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                newElements.put(new Position(x, y), getElementAt(new Position(TILE_SIZE - x -1, y)));
            }
        }
        return new ImageTile(getWidth(), getHeight(), newElements, getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageTile imageTile = (ImageTile) o;
        return id == imageTile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
