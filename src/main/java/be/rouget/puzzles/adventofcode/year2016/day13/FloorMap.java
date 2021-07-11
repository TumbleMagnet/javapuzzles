package be.rouget.puzzles.adventofcode.year2016.day13;

import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FloorMap {

    private final Map<Position, MapSquare> elements = Maps.newHashMap();
    private final int designerNumber;

    public FloorMap(int designerNumber) {
        this.designerNumber = designerNumber;
    }

    public MapSquare valueAtPosition(Position position) {
        MapSquare knownValue = elements.get(position);
        if (knownValue != null) {
            return knownValue;
        }
        MapSquare newValue = computeMapLocation(position);
        elements.put(position, newValue);
        return newValue;
    }

    private MapSquare computeMapLocation(Position position) {
        int x = position.getX();
        int y = position.getY();
        int value = x*x + 3*x + 2*x*y + y + y*y + designerNumber;
        String binaryValue = Integer.toBinaryString(value);
        int numberOfOnes = StringUtils.countMatches(binaryValue, '1');
        return (numberOfOnes %2 == 0 ? MapSquare.OPEN : MapSquare.WALL);
    }

    public RectangleMap<MapSquare> exportFromTopLeft(int width, int height) {
        Map<Position, MapSquare> elements = Maps.newHashMap();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position position = new Position(x, y);
                elements.put(position, computeMapLocation(position));
            }
        }
        return new RectangleMap<>(width, height, elements);
    }

    public int lengthOfShortestPath(Position from, Position to) {
        FloorGraph graph = new FloorGraph(this);
        return Dijkstra.shortestDistance(graph, from, p -> p.equals(to));
    }

    public int numberOfPositionsReachable(Position from, int moves) {
        FloorGraph graph = new FloorGraph(this);
        Set<Position> visitedPositions = Sets.newHashSet(from);
        Set<Position> positionsToVisit = Sets.newHashSet(from);

        for (int i = 0; i < moves; i++) {
            Set<Position> newPositions = positionsToVisit.stream()
                    .flatMap(position -> graph.edgesFrom(position).stream())
                    .map(Edge::getTo)
                    .filter(p -> !visitedPositions.contains(p))
                    .collect(Collectors.toSet());
            visitedPositions.addAll(newPositions);
            positionsToVisit = newPositions;
        }
        return visitedPositions.size();
    }

    public boolean isPositionInMap(Position position) {
        return position.getX() >= 0 && position.getY() >= 0;
    }

    public static class FloorGraph implements Graph<Position> {

        private final FloorMap floorMap;

        public FloorGraph(FloorMap floorMap) {
            this.floorMap = floorMap;
        }

        @Override
        public List<Edge<Position>> edgesFrom(Position position) {
            return List.of(
                    new Position(position.getX(), position.getY() - 1),
                    new Position(position.getX() + 1, position.getY()),
                    new Position(position.getX(), position.getY() + 1),
                    new Position(position.getX() - 1, position.getY())
            ).stream()
                    .filter(floorMap::isPositionInMap)
                    .filter(p -> floorMap.valueAtPosition(p) == MapSquare.OPEN)
                    .map(p -> new Edge<>(position, p, 1)).collect(Collectors.toList());
        }
    }

}
