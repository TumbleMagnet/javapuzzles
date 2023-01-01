package be.rouget.puzzles.adventofcode.year2022.day08;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;

import java.util.List;

public class TreeMap extends RectangleMap<Tree> {

    public TreeMap(List<String> input) {
        super(input, Tree::parse);
    }

    public long countVisibleTrees() {
        return getElements().stream()
                .filter(element -> isVisible(element.getKey(), element.getValue()))
                .count();
    }

    private boolean isVisible(Position position, Tree tree) {

        // For every direction:
        // - collect list of tree from position to the edge
        // - if all trees to the edge are smaller then tree is visible (done)
        for (Direction direction : Direction.values()) {
            boolean isHidden = pathToEdge(position, direction).stream()
                    .map(p -> getElementAt(p).size())
                    .anyMatch(size -> size >= tree.size());
            if (!isHidden) {
                // Tree is visible
                return true;
            }
        }

        // Tree is not visible
        return false;
    }

    private List<Position> pathToEdge(Position position, Direction direction) {
        List<Position> path = Lists.newArrayList();
        Position current = position.getNeighbour(direction);
        while (isPositionInMap(current)) {
            path.add(current);
            current = current.getNeighbour(direction);
        }
        return path;
    }

    public long highestScenicScore() {
        return getElements().stream()
                .mapToLong(element -> computeScenicScore(element.getKey(), element.getValue()))
                .max()
                .orElseThrow();
    }

    private long computeScenicScore(Position position, Tree tree) {

        long score = 1L;
        
        // For every direction, compute the number of trees visible and update the scenic score
        for (Direction direction : Direction.values()) {
            List<Position> pathToEdge = pathToEdge(position, direction);
            if (pathToEdge.isEmpty()) {
                // Optimization: when tree is on edge, scenic score is zero
                return 0L;
            }
            
            // Compute number of trees that are smaller in that direction
            long numberOfVisibleSmallerTrees = pathToEdge.stream()
                    .takeWhile(p -> getElementAt(p).size() < tree.size())
                    .count();
            
            // Add the tree that is taller or equal to current tree (unless we have reached the edge without finding one)
            long numberOfVisibleTrees = Math.min(numberOfVisibleSmallerTrees + 1, pathToEdge.size());
            
            score = score * numberOfVisibleTrees;
        }
        return score;
    }
}
