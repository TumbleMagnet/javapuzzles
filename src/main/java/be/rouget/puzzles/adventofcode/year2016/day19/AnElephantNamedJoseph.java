package be.rouget.puzzles.adventofcode.year2016.day19;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AnElephantNamedJoseph {

    private static final Logger LOG = LogManager.getLogger(AnElephantNamedJoseph.class);
    private static final int STARTING_COUNT = 3018458;

    public static void main(String[] args) {
        AnElephantNamedJoseph aoc = new AnElephantNamedJoseph();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return whoGetsPresents(STARTING_COUNT);
    }

    public static int whoGetsPresents(int count) {
        if (count == 1) {
            return 1;
        }

        if (count % 2 == 0) {
            return 2 * whoGetsPresents(count/2) -1;
        } else {
            return 2 * whoGetsPresents((count-1)/2) +1;
        }
    }

    public long computeResultForPart2() {
        return -1;
    }
}