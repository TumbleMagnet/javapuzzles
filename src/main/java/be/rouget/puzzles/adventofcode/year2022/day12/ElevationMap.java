package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ElevationMap extends RectangleMap<MapHeight> implements Graph<Position> {

    private final Position start;
    private final Position end;
    
    public  ElevationMap(List<String> input) {
        super(input, MapHeight::new);

        start = getElements().stream()
                .filter(e -> e.getValue().isStart())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();

        end = getElements().stream()
                .filter(e -> e.getValue().isEnd())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public List<Edge<Position>> edgesFrom(Position from) {
        return Arrays.stream(Direction.values())
                .map(from::getNeighbour)
                .filter(this::isPositionInMap)
                .filter(neighbor -> getElementAt(neighbor).getElevation() <= getElementAt(from).getElevation() + 1)
                .map(neighbor -> new Edge<>(from, neighbor, 1))
                .toList();
    }
}
