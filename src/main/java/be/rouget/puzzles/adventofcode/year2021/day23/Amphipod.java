package be.rouget.puzzles.adventofcode.year2021.day23;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Amphipod {

    private static final Logger LOG = LogManager.getLogger(Amphipod.class);

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(Amphipod.class);
        Amphipod aoc = new Amphipod(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public Amphipod(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
    }

    public long computeResultForPart1() {
        AmphipodMap.setRoomSize(2);
        return Dijkstra.shortestDistance(new AmphipodGraph(), AmphipodMap.initialMapForPart1(), AmphipodMap::isTarget);
    }

    public long computeResultForPart2() {
        AmphipodMap.setRoomSize(4);
        return Dijkstra.shortestDistance(new AmphipodGraph(), AmphipodMap.initialMapForPart2(), AmphipodMap::isTarget);
    }
}