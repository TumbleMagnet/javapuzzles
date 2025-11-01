package be.rouget.puzzles.adventofcode.year2024.day10;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


public class HoofIt {

    private static final Logger LOG = LogManager.getLogger(HoofIt.class);

    private final RectangleMap<HeightChar> topographicMap;
    private final List<Position> trailheads;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(HoofIt.class);
        HoofIt aoc = new HoofIt(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public HoofIt(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        // Parse topographic map
        topographicMap = new RectangleMap<>(input, HeightChar::parse);
        LOG.info("Map has size: {} x {}", topographicMap.getWidth(), topographicMap.getHeight());

        // Extract positions of trailheads
        trailheads = topographicMap.getElements().stream()
                .filter(entry -> entry.getValue().height() == 0)
                .map(Map.Entry::getKey)
                .toList();
        LOG.info("Found {} trailheads....", trailheads.size());
    }

    public long computeResultForPart1() {
        return trailheads.stream()
                .mapToLong(this::computeScore)
                .sum();
    }

    public long computeScore(Position trailhead) {
        // The score of a trailhead is the number of distinct summits that are reachable via hiking trails starting this position
        return reachablePositions(Sets.newHashSet(), Set.of(trailhead)).stream()
                .filter(position -> topographicMap.getElementAt(position).height() == 9)
                .count();
    }

    private Set<Position> reachablePositions(Set<Position> visitedPositions, Set<Position> currentPositions) {
        if (currentPositions.isEmpty()) {
            return visitedPositions;
        }
        // As hiking trails have constantly increasing height, there are no possible loops/cycles
        Set<Position> newPositions = currentPositions.stream()
                .map(this::possibleMoves)
                .flatMap(Set::stream)
                .collect(Collectors.toUnmodifiableSet());
        visitedPositions.addAll(newPositions);
        return reachablePositions(visitedPositions, newPositions);
    }

    private Set<Position> possibleMoves(Position currentPosition) {
        int currentHeight = topographicMap.getElementAt(currentPosition).height();
        return Arrays.stream(Direction.values())
                .map(currentPosition::getNeighbour)
                .filter(topographicMap::isPositionInMap)
                .filter(position -> topographicMap.getElementAt(position).height() == currentHeight + 1)
                .collect(Collectors.toUnmodifiableSet());
    }

    public long computeResultForPart2() {
        return trailheads.stream()
                .mapToLong(this::computeRating)
                .sum();
    }

    public long computeRating(Position trailhead) {
        // The rating of a trailhead is the number of distinct hiking trails which start from this position
        return countHikingTrails(0L, List.of(trailhead));
    }

    private long countHikingTrails(long currentCount, List<Position> currentPositions) {

        if (currentPositions.isEmpty()) {
            return currentCount;
        }

        // As hiking trails have constantly increasing height, there are no possible loops/cycles
        List<Position> newPositions = currentPositions.stream()
                .map(this::possibleMoves)
                .flatMap(Set::stream)
                .toList();

        // Count new hiking trails that have been completed
        long newTrailCount = newPositions.stream()
                .filter(this::isSummit)
                .count();

        // Continue exploration only for positions that are not summits
        List<Position> toExplore = newPositions.stream()
                .filter(position -> !isSummit(position))
                .toList();

        return countHikingTrails(currentCount + newTrailCount, toExplore);
    }

    private boolean isSummit(Position position) {
        return topographicMap.getElementAt(position).height() == 9;
    }
}