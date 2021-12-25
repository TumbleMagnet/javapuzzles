package be.rouget.puzzles.adventofcode.year2021.day15;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.graph.Dijkstra;
import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Chiton {

    private static final String YEAR = "2021";
    private static final String DAY = "15";

    private static final Logger LOG = LogManager.getLogger(Chiton.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        Chiton aoc = new Chiton(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public Chiton(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.input = input;
    }

    public long computeResultForPart1() {
        ChitonCave cave = new ChitonCave(input);
        Position start = new Position(0, 0);
        Position end = new Position(cave.getWidth() - 1, cave.getHeight() - 1);
        return Dijkstra.shortestDistance(cave, start, p -> p.equals(end));
    }

    public long computeResultForPart2() {
        ExtendedChitonCave cave = new ExtendedChitonCave(input);
        Position start = new Position(0, 0);
        Position end = new Position(cave.getWidth() - 1, cave.getHeight() - 1);
        return Dijkstra.shortestDistance(cave, start, p -> p.equals(end));
    }
}