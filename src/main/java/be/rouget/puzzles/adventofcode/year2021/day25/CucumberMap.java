package be.rouget.puzzles.adventofcode.year2021.day25;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CucumberMap {

    private final RectangleMap<CucumberMapSquare> rectangleMap;

    public CucumberMap(List<String> input) {
        this(new RectangleMap<>(input, CucumberMapSquare::fromMapChar));
    }

    public CucumberMap(RectangleMap<CucumberMapSquare> rectangleMap) {
        this.rectangleMap = rectangleMap;
    }

    public CucumberMap step() {

        // Move east-facing cucumbers
        CucumberMap result = step(CucumberMapSquare.EAST);

        // Move south-facing cucumbers
        result = result.step(CucumberMapSquare.SOUTH);

        return result;
    }

    private CucumberMap step(CucumberMapSquare cucumberType) {
        Map<Position, CucumberMapSquare> newElements = Maps.newHashMap();

        // Add elements which do not move
        this.rectangleMap.getElements().stream()
                .filter(entry -> entry.getValue() != cucumberType)
                .forEach(entry -> newElements.put(entry.getKey(), entry.getValue()));

        // Add cucumbers which are moving
        this.rectangleMap.getElements().stream()
                .filter(entry -> entry.getValue() == cucumberType)
                .forEach(entry -> {
                    newElements.put(entry.getKey(), CucumberMapSquare.EMPTY);
                    newElements.put(move(entry.getKey(), entry.getValue()), entry.getValue());
                });

        return new CucumberMap(new RectangleMap<>(this.rectangleMap.getWidth(), this.rectangleMap.getHeight(), newElements));
    }

    private Position move(Position currentPosition, CucumberMapSquare cucumberType) {
        Position targetPosition = computeTargetPosition(currentPosition, cucumberType);
        if (rectangleMap.getElementAt(targetPosition) == CucumberMapSquare.EMPTY) {
            // Cucumber can move to target position
            return targetPosition;
        } else {
            // Cucumber cannot move
            return currentPosition;
        }
    }

    private Position computeTargetPosition(Position currentPosition, CucumberMapSquare cucumberType) {
        if (cucumberType == CucumberMapSquare.EAST) {
            int targetX = (currentPosition.getX() + 1) % this.rectangleMap.getWidth();
            return new Position(targetX, currentPosition.getY());
        } else if (cucumberType == CucumberMapSquare.SOUTH) {
            int targetY = (currentPosition.getY() + 1) % this.rectangleMap.getHeight();
            return new Position(currentPosition.getX(), targetY);
        }
        throw new IllegalArgumentException("Cannot move square of type " + cucumberType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CucumberMap that = (CucumberMap) o;
        return Objects.equals(rectangleMap, that.rectangleMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangleMap);
    }

    @Override
    public String toString() {
        return rectangleMap.toString();
    }
}
