package be.rouget.puzzles.adventofcode.year2024.day12;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


public class GardenGroups {

    private static final Logger LOG = LogManager.getLogger(GardenGroups.class);
    private final RectangleMap<Plant> plantMap;
    private Set<Region> regions;

    @SuppressWarnings("java:S2629")
    static void main() {
        List<String> input = SolverUtils.readInput(GardenGroups.class);
        GardenGroups aoc = new GardenGroups(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public GardenGroups(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        plantMap = new RectangleMap<>(input, Plant::new);
        LOG.info("Plant maps is {} x {}", plantMap.getWidth(), plantMap.getHeight());
        Set<String> plantTypes = plantMap.getElements().stream()
                .map(e -> e.getValue().getMapChar())
                .collect(Collectors.toSet());
        LOG.info("There are {} plant types", plantTypes.size());
        extractRegions();
        LOG.info("There are {} regions", regions.size());
    }

    public long computeResultForPart1() {
        return regions.stream()
                .mapToLong(Region::computePriceForPart1)
                .sum();
    }

    public long computeResultForPart2() {
        return regions.stream()
                .mapToLong(Region::computePriceForPart2)
                .sum();
    }

    private void extractRegions() {
        regions = new HashSet<>();
        for (Map.Entry<Position, Plant> entry : plantMap.getElements()) {
            Position currentPosition = entry.getKey();
            LOG.debug("Processing position {},{}", currentPosition.getX(), currentPosition.getY());
            Plant currentPlant = entry.getValue();
            boolean alreadyInExistingRegion = regions.stream()
                    .anyMatch(region -> region.contains(currentPlant, currentPosition));
            if (!alreadyInExistingRegion) {
                LOG.debug("Extracting new region...");
                Region newRegion = extractRegion(currentPosition);
                LOG.debug("New region found with size {}", newRegion.getPositions().size());
                regions.add(newRegion);
            } else {
                LOG.debug("Position is already in an existing region");
            }
        }
    }

    private Region extractRegion(Position startPosition) {
        Plant plant = plantMap.getElementAt(startPosition);
        Set<Position> region = new HashSet<>();
        Queue<Position> toVisit = new ArrayDeque<>(List.of(startPosition));
        while (!toVisit.isEmpty()) {
            Position current = toVisit.remove();
            LOG.debug("Visiting position {}", current);
            LOG.debug("Remains {} positions to visit: {}", toVisit.size(), toVisit);
            region.add(current);
            Set<Position> neighbours = Arrays.stream(Direction.values())
                    .map(current::getNeighbour)
                    .filter(plantMap::isPositionInMap)
                    .filter(candidate -> plant.equals(plantMap.getElementAt(candidate)))
                    .filter(candidate -> !region.contains(candidate))
                    .filter(candidate -> !toVisit.contains(candidate))
                    .collect(Collectors.toSet());
            LOG.debug("Adding neightbours {}...", neighbours);
            toVisit.addAll(neighbours);
        }
        return new Region(plant, region);
    }
}