
package be.rouget.puzzles.adventofcode.year2021.day12;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.SimpleGraph;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PassagePathing {

    private static final String YEAR = "2021";
    private static final String DAY = "12";

    private static final Logger LOG = LogManager.getLogger(PassagePathing.class);
    private final SimpleGraph<Cave> caveGraph;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        PassagePathing aoc = new PassagePathing(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public PassagePathing(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        caveGraph = new SimpleGraph<>();
        for (String line : input) {
            String[] vertices = line.split("-");
            if (vertices.length != 2) {
                throw new IllegalArgumentException("Cannot parse vertices for line " + line);
            }
            caveGraph.addUndirectedEdge(new Cave(vertices[0]), new Cave(vertices[1]), 1);
        }
    }

    public long computeResultForPart1() {
        return countDifferentPaths(this::canVisitForPart1);
    }

    public long computeResultForPart2() {
        return countDifferentPaths(this::canVisitForPart2);
    }

    private long countDifferentPaths(BiFunction<List<Cave>, Cave, Boolean> visitCriteria) {
        Cave start = caveGraph.getVertex("start");
        Cave end = caveGraph.getVertex("end");
        return explore(List.of(start), end, visitCriteria);
    }

    private long explore(List<Cave> currentPath, Cave target, BiFunction<List<Cave>, Cave, Boolean> visitCriteria) {
        Cave lastCave = currentPath.get(currentPath.size() - 1);
        List<Cave> nextCaves = caveGraph.edgesFrom(lastCave).stream()
                .map(Edge::getTo)
                .filter(cave -> visitCriteria.apply(currentPath, cave))
                .collect(Collectors.toList());
        long completePaths = 0;
        for (Cave nextCave : nextCaves) {
            List<Cave> newPath = Lists.newArrayList(currentPath);
            newPath.add(nextCave);
            if (nextCave.equals(target)) {
                completePaths++;
            }
            else {
                completePaths += explore(newPath, target, visitCriteria);
            }
        }
        return completePaths;
    }

    private boolean canVisitForPart1(List<Cave> currentPath, Cave nextCave) {
        return nextCave.isLarge() || !currentPath.contains(nextCave);
    }

    private boolean canVisitForPart2(List<Cave> currentPath, Cave nextCave) {
        if ("start".equals(nextCave.getName())) {
            return false;
        }
        return nextCave.isLarge()
                || !currentPath.contains(nextCave) // small cave is not already visited
                || smallCavesHaveBeenVisitedOnlyOnce(currentPath);
    }

    private boolean smallCavesHaveBeenVisitedOnlyOnce(List<Cave> currentPath) {
        boolean aSmallCaveWasVisitedTwice = currentPath.stream()
                .filter(cave -> !cave.isLarge())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .anyMatch(p -> p.getValue() > 1);
        return !aSmallCaveWasVisitedTwice;
    }


}