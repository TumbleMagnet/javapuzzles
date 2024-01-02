package be.rouget.puzzles.adventofcode.year2023.day10;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.PipeCyclePosition;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class PipeMaze {

    private static final Logger LOG = LogManager.getLogger(PipeMaze.class);
    
    private final RectangleMap<PipeTile> pipeMap;
    private final Position startPosition;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PipeMaze.class);
        PipeMaze aoc = new PipeMaze(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PipeMaze(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        pipeMap = new RectangleMap<>(input, PipeTile::parse);
        startPosition = pipeMap.getElements().stream()
                .filter(element -> element.getValue() == PipeTile.START)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find start position"));
        LOG.info("Start position is {}", startPosition);
    }

    public long computeResultForPart1() {
        
        // Find one of the ends of the pipe cycle which are connected to the starting position
        PipeCyclePosition current = getStartOfPipeCycle();
        LOG.info("Found start of pipe at {} with tile {}", current, pipeMap.getElementAt(current.position()).getMapChar());

        // Walk the cycle completely until we reach the start position again
        while (!current.position().equals(startPosition)) {
            current = nextPositionInCycle(current);
        }
        
        // The distance to the farthest point of the cycle is half of the cycle length
        return current.stepsFromStart() / 2;
    }
    
    private PipeCyclePosition getStartOfPipeCycle() {
        for (Direction directionFromStart : Direction.values()) {
            Position neighbourPosition = startPosition.getNeighbour(directionFromStart);
            if (getConnectedNeighbours(neighbourPosition).contains(startPosition)) {
                // Found a pipe tile connected to the start position
                return new PipeCyclePosition(neighbourPosition, directionFromStart.reverse(), 1);
            }
        }
        throw new IllegalStateException("Could not find any pipe connected to the starting position!");
    }

    private Set<Position> getConnectedNeighbours(Position pipePosition) {
        PipeTile tile = pipeMap.getElementAt(pipePosition);
        if (tile.getConnections().isEmpty()) {
            return Set.of();
        }
        return tile.getConnections().stream()
                .map(pipePosition::getNeighbour)
                .collect(Collectors.toSet());
    }

    private PipeCyclePosition nextPositionInCycle(PipeCyclePosition cyclePosition) {

        // Direction of exit is the direction other than the direction from entry
        PipeTile tile = pipeMap.getElementAt(cyclePosition.position());
        Direction exitDirection = tile.getConnections().stream()
                .filter(direction -> !direction.equals(cyclePosition.fromDirection()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find exit direction for position " + cyclePosition));

        Position neighbourPosition = cyclePosition.position().getNeighbour(exitDirection);
        return new PipeCyclePosition(neighbourPosition, exitDirection.reverse(), cyclePosition.stepsFromStart()+1);
    }
    
    
    public long computeResultForPart2() {
        
        // TODO
        
        // Option 1: from https://www.reddit.com/r/adventofcode/comments/18f1sgh/2023_day_10_part_2_advise_on_part_2/
        // - compute area of polygon delimited by the pipe cycle using the "shoelace formula": https://en.wikipedia.org/wiki/Shoelace_formula
        // - use Pick's theorem to deduce the number of points inside the polygon: https://en.wikipedia.org/wiki/Pick%27s_theorem
        
        // Option 2: detect whether a point is inside the cycle by counting how times it crosses cycle tiles in every 4 direction:
        // if odd in every direction, the point is inside the cycle.
        // Note: do not count the - cycle pipes on the horizontal axes and the | cycle pipes on the vertical axes. 
        
        return -1;
    }
}