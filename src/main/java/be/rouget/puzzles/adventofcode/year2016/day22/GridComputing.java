package be.rouget.puzzles.adventofcode.year2016.day22;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.AStar;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class GridComputing {

    private static final Logger LOG = LogManager.getLogger(GridComputing.class);
    private final List<Node> nodes;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(GridComputing.class);
        GridComputing aoc = new GridComputing(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public GridComputing(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        nodes = input.subList(2, input.size()).stream()
                .map(Node::parse)
                .collect(Collectors.toList());
        LOG.info("Parsed {} nodes...", nodes.size());
    }

    public long computeResultForPart1() {
        return nodes.stream()
                .filter(n -> n.getUsed() > 0)
                .mapToLong(this::countViablePairs)
                .sum();
    }

    private long countViablePairs(Node source) {
        return nodes.stream()
                .filter(n -> !n.equals(source))
                .filter(n -> n.getAvailable() >= source.getUsed())
                .count();
    }

    public long computeResultForPart2() {

        int maxX = nodes.stream()
                .map(node -> node.getPosition())
                .filter(p -> p.getY() == 0)
                .mapToInt(Position::getX)
                .max().orElseThrow();
        Position targetData = new Position(maxX, 0);
        LOG.info("Position of target data is {}", targetData);
        SearchState initialState = new SearchState(Sets.newHashSet(this.nodes), targetData);

        return AStar.shortestDistance(new SearchGraph(), initialState, SearchState::isTargetState,
                state -> state.getTargetData().computeManhattanDistance(SearchState.FINAL_POSITION));
//        return Dijkstra.shortestDistance(new SearchGraph(), initialState, SearchState::isTargetState);
    }
}