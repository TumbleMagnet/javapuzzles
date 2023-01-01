package be.rouget.puzzles.adventofcode.year2022.day08;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class TreetopTreeHouse {

    private static final Logger LOG = LogManager.getLogger(TreetopTreeHouse.class);
    private final TreeMap treeMap;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(TreetopTreeHouse.class);
        TreetopTreeHouse aoc = new TreetopTreeHouse(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public TreetopTreeHouse(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        treeMap = new TreeMap(input);
    }

    public long computeResultForPart1() {
        return treeMap.countVisibleTrees();
    }
    
    public long computeResultForPart2() {
        return treeMap.highestScenicScore();
    }
}