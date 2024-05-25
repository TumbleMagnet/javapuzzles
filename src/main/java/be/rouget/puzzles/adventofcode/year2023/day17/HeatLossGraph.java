package be.rouget.puzzles.adventofcode.year2023.day17;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

public class HeatLossGraph implements Graph<SearchState> {

    private final RectangleMap<HeatLossTile> heatLossMap;
    private final boolean withUltraCrucibles;

    public HeatLossGraph(RectangleMap<HeatLossTile> heatLossMap, boolean withUltraCrucibles) {
        this.heatLossMap = heatLossMap;
        this.withUltraCrucibles = withUltraCrucibles;
    }

    public SearchState getStartState() {
        return new SearchState(new Position(0, 0), null, 0);
    }

    @Override
    public List<Edge<SearchState>> edgesFrom(SearchState from) {

        // If starting position, we have no direction yet and possible moves are right and down
        if (from.direction() == null && new Position(0, 0).equals(from.position())) {
            return toEdges(from, List.of(
                    new SearchState(from.position().getNeighbour(Direction.RIGHT), Direction.RIGHT, 1),
                    new SearchState(from.position().getNeighbour(Direction.DOWN), Direction.DOWN, 1)));
        }

        return withUltraCrucibles? toEdges(from, possibleMovesForPart2(from)) : toEdges(from, possibleMovesForPart1(from));
    }

    private List<Edge<SearchState>> toEdges(SearchState from, List<SearchState> targetStates) {
        return targetStates.stream()
                .map(to -> new Edge<>(from, to, heatLossMap.getElementAt(to.position()).heatLoss()))
                .toList();
    }

    public boolean isTargetState(SearchState searchState) {
        return (searchState.position().getX() == (heatLossMap.getWidth() - 1)) && (searchState.position().getY() == (heatLossMap.getHeight()) - 1);
    }

    private List<SearchState> possibleMovesForPart1(SearchState from) {
        // Possible moves are:
        // - left, ahead and right
        // - no more than 3 moves in straight line
        return Stream.of(from.turnLeft(), from.ahead(), from.turnRight())
                .filter(searchState -> searchState.movesInStraightLine() <= 3)
                .filter(searchState -> heatLossMap.isPositionInMap(searchState.position()))
                .toList();
    }
    
    private List<SearchState> possibleMovesForPart2(SearchState from) {
        // Possible moves are:
        // - left, ahead and right
        // - cannot turn until at least 4 moves in same direction
        // - no more than 10 moves in straight line
        List<SearchState> possibleMoves = Lists.newArrayList();
        if (from.movesInStraightLine() >= 4) {
            possibleMoves.add(from.turnLeft());
            possibleMoves.add(from.turnRight());
        }
        if (from.movesInStraightLine() < 10) {
            possibleMoves.add(from.ahead());
        }
        return possibleMoves.stream()
                .filter(searchState -> heatLossMap.isPositionInMap(searchState.position()))
                .toList();
    }
}
