package be.rouget.puzzles.adventofcode.year2023.day02;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CubeConundrum {

    private static final Logger LOG = LogManager.getLogger(CubeConundrum.class);
    
    private final List<Game> games;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CubeConundrum.class);
        CubeConundrum aoc = new CubeConundrum(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CubeConundrum(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        games = input.stream()
                .map(Game::parseGame)
                .toList();
    }

    public long computeResultForPart1() {
        Cubes quantities = new Cubes(12, 13, 14);
        return games.stream()
                .filter(g -> g.isCompatibleWithQuantities(quantities))
                .mapToInt(Game::index)
                .sum();
    }
    
    public long computeResultForPart2() {
        return games.stream()
                .map(Game::computeMinimumQuantities)
                .mapToInt(Cubes::computePower)
                .sum();
    }
}