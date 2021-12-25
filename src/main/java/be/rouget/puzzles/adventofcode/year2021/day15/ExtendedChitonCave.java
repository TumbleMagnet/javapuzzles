package be.rouget.puzzles.adventofcode.year2021.day15;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.List;
import java.util.stream.Collectors;

public class ExtendedChitonCave implements Graph<Position> {

    private static final int FACTOR = 5;

    private final RectangleMap<RiskLevel> caveMap;

    public ExtendedChitonCave(List<String> input) {
        caveMap = new RectangleMap<>(input, RiskLevel::fromMapChar);
    }

    public int getWidth() {
        return caveMap.getWidth() * FACTOR;
    }

    public int getHeight() {
        return caveMap.getHeight() * FACTOR;
    }

    public RiskLevel getElementAt(Position position) {
        int titleX = position.getX() / caveMap.getWidth();
        int x = position.getX() % caveMap.getWidth();
        int titleY = position.getY() / caveMap.getHeight();
        int y = position.getY() % caveMap.getWidth();

        int result = caveMap.getElementAt(new Position(x, y)).getValue() + titleX + titleY;
        if (result > 9) {
            result = result -9;
        }
        return new RiskLevel(result);
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getX() < getWidth()
                && position.getY() >= 0 && position.getY() < getHeight();
    }

    public List<Position> enumerateNeighbourPositions(Position position) {
        if (!isPositionInMap(position)) {
            throw new IllegalArgumentException("Position " + position.toString() + " is not in map!");
        }
        return position.enumerateNeighbours().stream()
                .filter(neighbour -> isPositionInMap(neighbour))
                .collect(Collectors.toList());
    }

    @Override
    public List<Edge<Position>> edgesFrom(Position from) {
        return enumerateNeighbourPositions(from).stream()
                .filter(p -> p.getX() == from.getX() || p.getY() == from.getY())
                .map(p -> new Edge<Position>(from, p, getElementAt(p).getValue()))
                .collect(Collectors.toList());
    }
}
