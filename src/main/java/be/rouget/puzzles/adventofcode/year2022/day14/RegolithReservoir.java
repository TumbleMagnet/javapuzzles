package be.rouget.puzzles.adventofcode.year2022.day14;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class RegolithReservoir {

    private static final Logger LOG = LogManager.getLogger(RegolithReservoir.class);
    
    private final List<RockPath> rockPaths;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(RegolithReservoir.class);
        RegolithReservoir aoc = new RegolithReservoir(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RegolithReservoir(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        rockPaths = input.stream()
                .map(RockPath::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return addSandUnitsAndReturnCount(false);
    }

    public long computeResultForPart2() {
        return addSandUnitsAndReturnCount(true);
    }

    private long addSandUnitsAndReturnCount(boolean hasFloor) {
        ReservoirMap reservoir = new ReservoirMap(rockPaths, hasFloor);
        long sandCount = 0;
        while (reservoir.addSandUnit()) {
            sandCount++;
        }
        return sandCount;
    }
}