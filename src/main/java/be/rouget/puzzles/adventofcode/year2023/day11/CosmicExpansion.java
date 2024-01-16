package be.rouget.puzzles.adventofcode.year2023.day11;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class CosmicExpansion {

    private static final Logger LOG = LogManager.getLogger(CosmicExpansion.class);
    private final RectangleMap<CosmicTile> cosmicMap;
    private final List<Position> galaxyPositions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CosmicExpansion.class);
        CosmicExpansion aoc = new CosmicExpansion(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CosmicExpansion(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        cosmicMap = new RectangleMap<>(input, CosmicTile::parse);
        LOG.info("Map is {} x {}", cosmicMap.getWidth(), cosmicMap.getHeight());
        // Extract initial positions of galaxies
        galaxyPositions = cosmicMap.getElements().stream()
                .filter(entry -> entry.getValue() == CosmicTile.GALAXY)
                .map(Map.Entry::getKey)
                .toList();
        LOG.info("Found {} galaxies...", galaxyPositions.size());
    }

    public long computeResultForPart1() {
        return expandUniverseAndMeasureDistances(2);
    }

    public long computeResultForPart2() {
        return expandUniverseAndMeasureDistances(1000000);
    }

    public long expandUniverseAndMeasureDistances(int expansionFactor) {

        List<Position> expandedGalaxies = galaxyPositions;
        
        // Do the vertical expansion by duplicating empty rows
        List<Integer> emptyRows = getEmptyRowsInDescendingOrder();
        LOG.info("Found {} empty rows...", emptyRows.size());
        for (Integer yOfEmptyRow : emptyRows) {
            // Shift down all galaxies lower than the row
            expandedGalaxies = expandedGalaxies.stream()
                    .map(p -> p.getY() > yOfEmptyRow ? new Position(p.getX(), p.getY()+expansionFactor-1) : new Position(p.getX(), p.getY()))
                    .toList();
        }

        // Do the horizontal expansion by duplicating empty columns
        List<Integer> emptyColumns = getEmptyColumnsInDescendingOrder();
        LOG.info("Found {} empty columns...", emptyColumns.size());
        for (Integer xOfEmptyColumn : emptyColumns) {
            // Shift right all galaxies to the right of the column
            expandedGalaxies = expandedGalaxies.stream()
                    .map(p -> p.getX() > xOfEmptyColumn ? new Position(p.getX()+expansionFactor-1, p.getY()) : new Position(p.getX(), p.getY()))
                    .toList();
        }

        // Compute the manhattan distance between the pairs of galaxies
        long totalDistance = 0L;
        for (int i = 0; i < expandedGalaxies.size()-1; i++) {
            for (int j = i+1; j < expandedGalaxies.size(); j++) {
                Position galaxy1 = expandedGalaxies.get(i);
                Position galaxy2 = expandedGalaxies.get(j);
                totalDistance += galaxy1.computeManhattanDistance(galaxy2);
            }
        }

        return totalDistance;
    }

    private List<Integer> getEmptyRowsInDescendingOrder() {
        return IntStream.rangeClosed(0, cosmicMap.getHeight()-1)
                .filter(this::isRowEmpty)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private boolean isRowEmpty(int y) {
        return IntStream.rangeClosed(0, cosmicMap.getWidth()-1)
                .mapToObj(x -> new Position(x, y))
                .map(cosmicMap::getElementAt)
                .allMatch(title -> title == CosmicTile.EMPTY);
    }

    private List<Integer> getEmptyColumnsInDescendingOrder() {
        return IntStream.rangeClosed(0, cosmicMap.getWidth()-1)
                .filter(this::isColumnEmpty)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private boolean isColumnEmpty(int x) {
        return IntStream.rangeClosed(0, cosmicMap.getHeight()-1)
                .mapToObj(y -> new Position(x, y))
                .map(cosmicMap::getElementAt)
                .allMatch(title -> title == CosmicTile.EMPTY);
    }
}