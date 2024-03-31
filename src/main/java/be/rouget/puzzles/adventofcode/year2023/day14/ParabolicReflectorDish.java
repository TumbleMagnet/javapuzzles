package be.rouget.puzzles.adventofcode.year2023.day14;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        ControlPlatform platform = new ControlPlatform(input);
        platform.title(Direction.UP);
        return platform.computeLoad();
    }

    public long computeResultForPart2() {

        ControlPlatform platform = new ControlPlatform(input);

        // Find a cycle in the positions after spinning
        Cycle cycle = detectCycle(platform);
        LOG.info("Found cycle: {}", cycle);

        // Compute index in cycle for target and do additional spins to put platform in target position
        long indexInCycle = (1000000000L - cycle.offset()) % cycle.length();
        LOG.info("Number of additional spins: {}", indexInCycle);
        for (int i = 0; i < indexInCycle; i++) {
            doOneSpinCycle(platform);
        }
        
        return platform.computeLoad();
    }

    private Cycle detectCycle(ControlPlatform platform) {

        // Detect cycles based on the position of round rocks only (instead of based on the whole platform)
        Map<Set<Position>, Long> previousPositions = Maps.newHashMap();

        long currentIndex = 1;
        while (true) {
            doOneSpinCycle(platform);
            Set<Position> currentPositions = platform.positionsOfRoundRocks();
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

    private static void doOneSpinCycle(ControlPlatform platform) {
        platform.title(Direction.UP);
        platform.title(Direction.LEFT);
        platform.title(Direction.DOWN);
        platform.title(Direction.RIGHT);
    }
}