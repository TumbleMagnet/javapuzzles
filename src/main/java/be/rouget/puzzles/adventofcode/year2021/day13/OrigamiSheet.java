package be.rouget.puzzles.adventofcode.year2021.day13;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public class OrigamiSheet {
    private final Set<Position> dots;

    public OrigamiSheet(Set<Position> dots) {
        this.dots = Sets.newHashSet(dots);
    }

    public OrigamiSheet fold(FoldInstruction instruction) {
        Set<Position> foldedDots = dots.stream()
                .map(p -> fold(p, instruction))
                .collect(Collectors.toSet());
        return new OrigamiSheet(foldedDots);
    }

    private Position fold(Position position, FoldInstruction instruction) {
        if (Orientation.HORIZONTAL == instruction.getOrientation()) {
            if (position.getY() < instruction.getCoordinate()) {
                return position;
            } else {
                return new Position(position.getX(), 2 * instruction.getCoordinate() - position.getY());
            }
        } else {
            if (position.getX() < instruction.getCoordinate()) {
                return position;
            } else {
                return new Position(2 * instruction.getCoordinate() - position.getX(), position.getY());
            }
        }
    }

    public int getNumberOfDots() {
        return dots.size();
    }

    public RectangleMap<OrigamiChar> toRectangleMap() {
        int maxX = dots.stream().mapToInt(Position::getX).max().orElseThrow();
        int maxY = dots.stream().mapToInt(Position::getY).max().orElseThrow();
        RectangleMap<OrigamiChar> rectangleMap = new RectangleMap<>(maxX + 1, maxY + 1, OrigamiChar.EMPTY);
        dots.forEach(d -> rectangleMap.setElementAt(d, OrigamiChar.DOT));
        return rectangleMap;
    }
}
