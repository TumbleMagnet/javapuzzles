package be.rouget.puzzles.adventofcode.year2020.day17;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RectangleMap<E extends MapCharacter> {

    private final int width;
    private final int height;
    private final Map<Position, E> elements = Maps.newHashMap();

    public RectangleMap(List<String> input, Function<String, E> mappingFunction) {

        String[] lines = input.toArray(new String[0]);
        this.height = lines.length;
        this.width = lines[0].length();

        for (int y = 0; y < height; y++) {
            String currentLine = lines[y];
            if (currentLine.length() != width) {
                throw new IllegalArgumentException("Line " + (y+1) + " has length " + currentLine.length() + " while expecting " + this.width);
            }
            String[] characters = AocStringUtils.splitCharacters(currentLine);
            for (int x = 0; x < characters.length; x++) {
                Position position = new Position(x, y);
                elements.put(position, mappingFunction.apply(characters[x]));
            }
        }
    }

    public RectangleMap(int width, int height, E initialElement) {
        this.width = width;
        this.height = height;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                elements.put(new Position(x, y), initialElement);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public E getElementAt(Position position) {
        if (!isPositionInMap(position)) {
            throw new IllegalArgumentException("Position " + position + " is not in map!");
        }
        return elements.get(position);
    }

    public void setElementAt(Position position, E element) {
        elements.put(position, element);
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < width
                && position.getY() >= 0 && position.getY() < height;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(getElementAt(new Position(x, y)).getMapChar());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
