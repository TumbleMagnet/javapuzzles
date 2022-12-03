package be.rouget.puzzles.adventofcode.year2016.day22;

import be.rouget.puzzles.adventofcode.util.map.Position;
import lombok.Value;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class SearchState {
    public static final Position FINAL_POSITION = new Position(0, 0);

    Set<Node> nodes;
    Position targetData;

    public boolean isTargetState() {
        return targetData.equals(FINAL_POSITION);
    }

    public Set<SearchState> possibleMoves() {

        // Consider only moves which consists in moving data to the empty node of the grid
        Node emptyNode = nodes.stream()
                .filter(n -> n.getUsed() == 0)
                .findFirst().orElseThrow();
        Position emptyPosition = emptyNode.getPosition();
        List<Position> possibleFromPositions = emptyPosition.enumerateNeighbours().stream()
                .filter(fromPosition -> fromPosition.computeManhattanDistance(emptyPosition) == 1)
                .collect(Collectors.toList());
        return nodes.stream()
                .filter(from -> possibleFromPositions.contains(from.getPosition()))
                .filter(from -> isMovePossible(from, emptyNode))
                .map(from -> doMove(from, emptyNode))
                .collect(Collectors.toSet());
    }

    public boolean isMovePossible(Node from, Node target) {
        return !target.equals(from)
                && target.isConnectedTo(from)
                && target.getAvailable() >= from.getUsed();
    }

    private SearchState doMove(Node from, Node to) {
        Set<Node> newNodes = this.nodes.stream()
                .filter(n -> !n.equals(from) && !n.equals(to))
                .collect(Collectors.toSet());
        newNodes.add(new Node(from.getName(), from.getSize(), 0, from.getPosition())); // Updated "from" node
        newNodes.add(new Node(to.getName(), to.getSize(), to.getUsed() + from.getUsed(), from.getPosition())); // Updated "to" node
        Position newTargetData = from.getPosition().equals(targetData) ? to.getPosition() : targetData;
        return new SearchState(newNodes, newTargetData);
    }
}
