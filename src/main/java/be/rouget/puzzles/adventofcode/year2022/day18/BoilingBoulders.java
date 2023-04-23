package be.rouget.puzzles.adventofcode.year2022.day18;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map3d.Position3D;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;


public class BoilingBoulders {

    private static final Logger LOG = LogManager.getLogger(BoilingBoulders.class);
    private final Set<Position3D> droplets;
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int minZ;
    private final int maxZ;
    
    private final Set<Position3D> trappedPositions = Sets.newHashSet();
    private final Set<Position3D> outsidePositions = Sets.newHashSet();

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(BoilingBoulders.class);
        BoilingBoulders aoc = new BoilingBoulders(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public BoilingBoulders(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        droplets = input.stream()
                .map(BoilingBoulders::parseLine)
                .collect(Collectors.toSet());
        minX = droplets.stream()
                .mapToInt(Position3D::x)
                .min().orElseThrow();
        maxX = droplets.stream()
                .mapToInt(Position3D::x)
                .max().orElseThrow();
        minY = droplets.stream()
                .mapToInt(Position3D::y)
                .min().orElseThrow();
        maxY = droplets.stream()
                .mapToInt(Position3D::y)
                .max().orElseThrow();
        minZ = droplets.stream()
                .mapToInt(Position3D::z)
                .min().orElseThrow();
        maxZ = droplets.stream()
                .mapToInt(Position3D::z)
                .max().orElseThrow();
        LOG.info("There are {} droplets", droplets.size());
        LOG.info("External boundaries: x[{}, {}], y[{}, {}], z[{}, {}]",
                minX, maxX,
                minY, maxY,
                minZ, maxZ
        );
    }

    public long computeResultForPart1() {
        return droplets.stream()
                .mapToLong(this::surfaceAreaForPart1)
                .sum();
    }
    
    public long computeResultForPart2() {
        return droplets.stream()
                .mapToLong(this::surfaceAreaForPart2)
                .sum();
    }

    private long surfaceAreaForPart1(Position3D droplet) {
        long numberOfActualFacetNeighbours = droplet.getFacetNeighbours().stream()
                .filter(droplets::contains)
                .count();
        return 6L - numberOfActualFacetNeighbours;
    }

    private long surfaceAreaForPart2(Position3D droplet) {
        return droplet.getFacetNeighbours().stream()
                .filter(neighbour -> !droplets.contains(neighbour)) // Free facet
                .filter(neighbour -> !isTrapped(neighbour)) // And not trapped within droplets
                .count();
    }

    private boolean isTrapped(Position3D position) {

        if (isOutside(position)) {
            return false;
        }

        if (trappedPositions.contains(position)) {
            // Known trapped position
            return true;
        }

        if (outsidePositions.contains(position)) {
            // Known position that is connected to outside
            return false;
        }
        
        Set<Position3D> pocket = Sets.newHashSet(position);
        // Grow pocket until:
        // - it cannot grow anymore => trapped
        // - it includes a position outside boundaries => not trapped
        while (true) {
            Set<Position3D> newPocketPositions = pocket.stream()
                    .flatMap(pocketPosition -> pocketPosition.getFacetNeighbours().stream()) // All neighbours
                    .filter(neighbour -> !droplets.contains(neighbour)) // that are free
                    .filter(neighbour -> !pocket.contains(neighbour))  // and not already in pocket
                    .collect(Collectors.toSet());
            if (newPocketPositions.isEmpty()) {
                // Initial position is trapped as pocket cannot grow anymore
                trappedPositions.addAll(pocket);
                return true;
            } else {
                boolean anyOutside = newPocketPositions.stream().anyMatch(this::isOutside);
                if (anyOutside) {
                    // Initial position is not trapped as pocket grew outside the boundaries of the droplet
                    outsidePositions.addAll(pocket);
                    outsidePositions.addAll(newPocketPositions);
                    return false;
                }
            }
            pocket.addAll(newPocketPositions);
        }
    }

    public boolean isOutside(Position3D position) {
        return position.x() < minX || position.x() > maxX
                || position.y() < minY || position.y() > maxY
                || position.z() < minZ || position.z() > maxZ;
    }

    public static Position3D parseLine(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Cannot parse line: " + line);
        }
        return new Position3D(parseInt(tokens[0]), parseInt(tokens[1]), parseInt(tokens[2]));
    }
}