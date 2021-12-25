package be.rouget.puzzles.adventofcode.year2021.day15;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.List;
import java.util.stream.Collectors;

public class ChitonCave implements Graph<Position> {

    private final RectangleMap<RiskLevel> caveMap;

    public ChitonCave(List<String> input) {
        caveMap = new RectangleMap<>(input, RiskLevel::fromMapChar);
    }

    public int getWidth() {
        return caveMap.getWidth();
    }

    public int getHeight() {
        return caveMap.getHeight();
    }

    @Override
    public List<Edge<Position>> edgesFrom(Position from) {
        return caveMap.enumerateNeighbourPositions(from).stream()
                .filter(p -> p.getX() == from.getX() || p.getY() == from.getY())
                .map(p -> new Edge<Position>(from, p, caveMap.getElementAt(p).getValue()))
                .collect(Collectors.toList());
    }
}
