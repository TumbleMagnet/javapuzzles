package be.rouget.puzzles.adventofcode.year2023.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;


public class TheFloorWillBeLava {

    private static final Logger LOG = LogManager.getLogger(TheFloorWillBeLava.class);

    private final RectangleMap<FloorTile> floorMap;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(TheFloorWillBeLava.class);
        TheFloorWillBeLava aoc = new TheFloorWillBeLava(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public TheFloorWillBeLava(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        floorMap = new RectangleMap<>(input, FloorTile::fromMapChar);
    }

    public long computeResultForPart1() {
        EnergizedTile start = new EnergizedTile(new Position(0, 0), Direction.RIGHT);
        return countEnergizedTileForStartPosition(start);
    }

    public long computeResultForPart2() {
        
        Set<EnergizedTile> startPositions = Sets.newHashSet();
        
        // Compute start positions for the top row
        for (int x = 0; x < floorMap.getWidth(); x++) {
            startPositions.add(new EnergizedTile(new Position(x, 0), Direction.DOWN));
        }
        
        // Compute start positions for the right column
        for (int y = 0; y < floorMap.getHeight(); y++) {
            startPositions.add(new EnergizedTile(new Position(floorMap.getWidth() - 1, y), Direction.LEFT));
        }
        
        // Compute start positions for the bottom row
        for (int x = 0; x < floorMap.getWidth(); x++) {
            startPositions.add(new EnergizedTile(new Position(x, floorMap.getHeight() - 1), Direction.UP));
        }
        
        // Compute start positions for the left column
        for (int y = 0; y < floorMap.getHeight(); y++) {
            startPositions.add(new EnergizedTile(new Position(0, y), Direction.RIGHT));
        }
        
        return startPositions.stream()
                .mapToLong(this::countEnergizedTileForStartPosition)
                .max()
                .orElseThrow();
    }

    private long countEnergizedTileForStartPosition(EnergizedTile start) {
        // Set of energized tiles to process
        Set<EnergizedTile> tilesToProcess = Sets.newHashSet(start);

        // Process tiles
        Set<EnergizedTile> energizedTiles = Sets.newHashSet();
        while (!tilesToProcess.isEmpty()) {
            EnergizedTile energizedTileToProcess = tilesToProcess.iterator().next();

            // If title is empty, add it to the list of energized tiles
            energizedTiles.add(energizedTileToProcess);

            // Compute new tiles to process (following the beam)
            Set<EnergizedTile> newTiles = computeOutgoingTiles(energizedTileToProcess);
            newTiles.stream()
                    .filter(tile -> !energizedTiles.contains(tile)) // Avoid loops
                    .forEach(tilesToProcess::add);

            // Remove the tile from the list of tiles to process
            tilesToProcess.remove(energizedTileToProcess);
        }

        // Return the number of energized tiles
        return energizedTiles.stream()
                .map(EnergizedTile::position)
                .distinct()
                .count();
    }


    private Set<EnergizedTile> computeOutgoingTiles(EnergizedTile energizedTile) {
        Set<EnergizedTile> newTiles = Sets.newHashSet();
        computeNewDirectionsAfterTile(energizedTile).stream()
                .map(direction -> new EnergizedTile(energizedTile.position().getNeighbour(direction), direction))
                .filter(tile -> floorMap.isPositionInMap(tile.position()))
                .forEach(newTiles::add);
        return newTiles;
    }

    private Set<Direction> computeNewDirectionsAfterTile(EnergizedTile energizedTile) {
        Set<Direction> newDirections = Sets.newHashSet();

        Direction direction = energizedTile.beamDirection();
        FloorTile floorTile = floorMap.getElementAt(energizedTile.position());

        if (floorTile.equals(FloorTile.EMPTY)) {
            // Continue in the same direction
            newDirections.add(direction);
        }
        else if (FloorTile.MIRROR_FORWARD.equals(floorTile)) {
            switch (direction) {
                case RIGHT -> newDirections.add(Direction.UP);
                case DOWN -> newDirections.add(Direction.LEFT);
                case LEFT -> newDirections.add(Direction.DOWN);
                case UP -> newDirections.add(Direction.RIGHT);
            }
        }
        else if (FloorTile.MIRROR_BACKWARD.equals(floorTile)) {
            switch (direction) {
                case RIGHT -> newDirections.add(Direction.DOWN);
                case DOWN -> newDirections.add(Direction.RIGHT);
                case LEFT -> newDirections.add(Direction.UP);
                case UP -> newDirections.add(Direction.LEFT);
            }
        }
        else if (FloorTile.SPLITTER_VERTICAL.equals(floorTile)) {
            if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
                newDirections.add(direction);
            }
            else {
                newDirections.add(direction.turnLeft());
                newDirections.add(direction.turnRight());
            }
        }
        else if (FloorTile.SPLITTER_HORIZONTAL.equals(floorTile)) {
            if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
                newDirections.add(direction);
            }
            else {
                newDirections.add(direction.turnLeft());
                newDirections.add(direction.turnRight());
            }
        }
        else {
            throw new IllegalArgumentException("Unknown tile type: " + floorTile);
        }
        
        return newDirections;
    }

}