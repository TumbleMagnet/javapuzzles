package be.rouget.puzzles.adventofcode.year2016.day13;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwistyMaze {

    private static final int DESIGNER_NUMBER = 1350;
    private static final Logger LOG = LogManager.getLogger(TwistyMaze.class);

    public static void main(String[] args) {
        TwistyMaze aoc = new TwistyMaze();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        FloorMap floorMap = new FloorMap(DESIGNER_NUMBER);
        return floorMap.lengthOfShortestPath(new Position(1, 1), new Position(31, 39));
    }

    public long computeResultForPart2() {
        FloorMap floorMap = new FloorMap(DESIGNER_NUMBER);
        return floorMap.numberOfPositionsReachable(new Position(1, 1), 50);
    }
}
