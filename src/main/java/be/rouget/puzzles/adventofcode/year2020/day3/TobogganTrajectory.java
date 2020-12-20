package be.rouget.puzzles.adventofcode.year2020.day3;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TobogganTrajectory {

    private static final String YEAR = "2020";
    private static final String DAY = "03";

    private static Logger LOG = LogManager.getLogger(TobogganTrajectory.class);

    private Map map;

    public TobogganTrajectory(List<String> input) {
        this.map = new Map(input);
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TobogganTrajectory aoc = new TobogganTrajectory(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        Slope slope = new Slope(3, 1);
        return countTreeForSlope(slope);
    }

    private int countTreeForSlope(Slope slope) {
        Position position = new Position(0, 0);
        int treeCount = 0;
        for (int step = 0; step < map.getHeight(); step++) {
            MapItem item = map.getItemAtPosition(position);
            if (item == MapItem.TREE) {
                treeCount++;
            }
            position = new Position(position.getX() + slope.getDeltaX(), position.getY() + slope.getDeltaY());
            if (position.getY() >= map.getHeight()) {
                break;
            }
        }

        return treeCount;
    }

    public long computeResultForPart2() {
        List<Slope> slopes = List.of(
                new Slope(1, 1),
                new Slope(3, 1),
                new Slope(5, 1),
                new Slope(7, 1),
                new Slope(1, 2)
        );
        long result = 1;
        for (Slope slope : slopes) {
            result = result * countTreeForSlope(slope);
        }
        return result;
    }
}