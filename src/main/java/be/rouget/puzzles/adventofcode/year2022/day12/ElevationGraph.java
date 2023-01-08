package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;

import java.util.Arrays;
import java.util.List;

public class ElevationGraph implements Graph<Position> {

    private final ElevationMap elevationMap;
    private final MoveDirection moveDirection;

    public ElevationGraph(ElevationMap elevationMap, MoveDirection moveDirection) {
        this.elevationMap = elevationMap;
        this.moveDirection = moveDirection;
    }

    @Override
    public List<Edge<Position>> edgesFrom(Position from) {
        return Arrays.stream(Direction.values())
                .map(from::getNeighbour)
                .filter(elevationMap::isPositionInMap)
                .filter(neighbor -> elevationMap.isValidMove(from, neighbor, moveDirection))
                .map(neighbor -> new Edge<>(from, neighbor, 1))
                .toList();
    }
}
