package be.rouget.puzzles.adventofcode.year2021.day25;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SeaCucumber {

    private static final Logger LOG = LogManager.getLogger(SeaCucumber.class);
    private final CucumberMap initialMap;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(SeaCucumber.class);
        SeaCucumber aoc = new SeaCucumber(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SeaCucumber(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        initialMap = new CucumberMap(input);
    }

    public long computeResultForPart1() {
        int step = 0;
        CucumberMap currentMap = this.initialMap;
        while (true) {
            step++;
            CucumberMap newMap = currentMap.step();
            if (newMap.equals(currentMap)) {
                return step;
            }
            currentMap = newMap;
            LOG.info("Step {}...", step);
        }
    }

    public long computeResultForPart2() {
        return -1;
    }
}