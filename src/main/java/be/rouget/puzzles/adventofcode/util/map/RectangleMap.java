package be.rouget.puzzles.adventofcode.util.map;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RectangleMap<E extends MapCharacter> {

    private final int width;
    private final int height;
    private final Map<Position, E> elements;

    public RectangleMap(List<String> input, Function<String, E> mappingFunction) {
        this.elements = Maps.newHashMap();
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
        this.elements = Maps.newHashMap();
        this.width = width;
        this.height = height;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                elements.put(new Position(x, y), initialElement);
            }
        }
    }

    public RectangleMap(int width, int height, Map<Position, E> elements) {
        this.width = width;
        this.height = height;
        this.elements = Maps.newHashMap(elements);
    }

    public RectangleMap(RectangleMap<E> original) {
        this(original.width, original.height, original.elements);
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

    public List<Position> enumerateNeighbourPositions(Position position) {
        if (!isPositionInMap(position)) {
            throw new IllegalArgumentException("Position " + position.toString() + " is not in map!");
        }

        return position.enumerateNeighbours().stream()
                .filter(neighbour -> isPositionInMap(neighbour))
                .collect(Collectors.toList());
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

    public Set<Map.Entry<Position, E>> getElements() {
        return elements.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectangleMap<?> that = (RectangleMap<?>) o;
        return width == that.width && height == that.height && elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, elements);
    }
}
