package be.rouget.puzzles.adventofcode.year2023.day14;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.year2023.day14.PlatformTile.EMPTY;
import static be.rouget.puzzles.adventofcode.year2023.day14.PlatformTile.ROUND_ROCK;

public class ParabolicReflectorDish {

    private static final Logger LOG = LogManager.getLogger(ParabolicReflectorDish.class);
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ParabolicReflectorDish.class);
        ParabolicReflectorDish aoc = new ParabolicReflectorDish(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ParabolicReflectorDish(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {

        RectangleMap<PlatformTile> platform = new RectangleMap<>(input, PlatformTile::parse);
        
        // Tile the platform and move the round rocks north until they cannot move anymore
        tilePlatform(platform, Direction.UP);

        return computeLoad(platform);
    }

    private static void tilePlatform(RectangleMap<PlatformTile> platform, Direction direction) {
        List<Position> positions = extractSortedPositions(platform, getComparatorForTileDirection(direction));
        boolean hasMoved = true;
        while (hasMoved) {
            hasMoved = false;
            for (Position start : positions) {
                if (platform.getElementAt(start).equals(ROUND_ROCK)) {
                    Optional<Position> possibleTarget = findTargetPosition(platform, start, direction);
                    if (possibleTarget.isPresent()) {
                        Position target = possibleTarget.get();
                        platform.setElementAt(start, EMPTY);
                        platform.setElementAt(target, ROUND_ROCK);
                        hasMoved = true;
                    }
                }
            }
        }
    }

    private static Optional<Position> findTargetPosition(RectangleMap<PlatformTile> platform, Position start, Direction direction) {
        Position target = start.getNeighbour(direction);
        if (platform.isPositionInMap(target) && platform.getElementAt(target).equals(EMPTY)) {
            // One move is possible, try additional moves in that direction
            Optional<Position> finalTarget = findTargetPosition(platform, target, direction);
            if (finalTarget.isPresent()) {
                return finalTarget;
            } else {
                return Optional.of(target);
            }
        }
        else {
            // Not possible to move in that direction
            return Optional.empty();
        }
    }

    private static Comparator<Position> getComparatorForTileDirection(Direction direction) {
        return switch (direction) {
            // When tiling UP, process rows to top to bottom 
            case UP -> Comparator.comparing(Position::getY).thenComparing(Position::getX);

            // When tiling DOWN, process rows to bottom to top 
            case DOWN -> Comparator.comparing(Position::getY).reversed().thenComparing(Position::getX);

            // When tiling RIGHT, process columns from right to left
            case RIGHT -> Comparator.comparing(Position::getX).reversed().thenComparing(Position::getY);

            // When tiling LEFT, process columns from left to right
            case LEFT -> Comparator.comparing(Position::getX).thenComparing(Position::getY);
        };
    }

    private static List<Position> extractSortedPositions(RectangleMap<PlatformTile> platform, Comparator<Position> comparator) {
        return platform.getElements().stream()
                .map(Map.Entry::getKey)
                .sorted(comparator)
                .toList();
    }

    private static long computeLoad(RectangleMap<PlatformTile> platform) {
        return platform.getElements().stream()
                .filter(entry -> entry.getValue() == ROUND_ROCK)
                .mapToLong(entry -> platform.getHeight() - entry.getKey().getY())
                .sum();
    }

    public long computeResultForPart2() {

        RectangleMap<PlatformTile> platform = new RectangleMap<>(input, PlatformTile::parse);

        Cycle cycle = detectCycle(platform);
        LOG.info("Found cycle: {}", cycle);

        // Compute index in cycle for target and do additional spins to put platform in position
        long indexInCycle = (1000000000L - cycle.offset()) % cycle.length();
        LOG.info("Number of additional spins: {}", indexInCycle);
        for (int i = 0; i < indexInCycle; i++) {
            doOneSpinCycle(platform);
        }
        return computeLoad(platform);
    }
    
    private Cycle detectCycle(RectangleMap<PlatformTile> platform) {
        
        // Detect cycles based on the position of round rocks only (instead of based on the whole platform)
        Map<Set<Position>, Long> previousPositions = Maps.newHashMap();
        
        long currentIndex = 1;
        while (true) {
            doOneSpinCycle(platform);
            Set<Position> currentPositions = positionsOfRoundRocks(platform);
            Long previousIndex = previousPositions.get(currentPositions);
            if (previousIndex != null) {
                // Found cycle
                return new Cycle(previousIndex, currentIndex - previousIndex);
            } else {
                // Current positions do not match any previous positions
                previousPositions.put(currentPositions, currentIndex);
                currentIndex++;
            }
        }
    }

    private static void doOneSpinCycle(RectangleMap<PlatformTile> platform) {
        tilePlatform(platform, Direction.UP);
        tilePlatform(platform, Direction.LEFT);
        tilePlatform(platform, Direction.DOWN);
        tilePlatform(platform, Direction.RIGHT);
    }
    
    private Set<Position> positionsOfRoundRocks(RectangleMap<PlatformTile> platform) {
        return platform.getElements().stream()
                .filter(entry -> entry.getValue() == ROUND_ROCK)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}