package be.rouget.puzzles.adventofcode.year2021.day20;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TrenchMap {

    private static final Logger LOG = LogManager.getLogger(TrenchMap.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(TrenchMap.class);
        TrenchMap aoc = new TrenchMap(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TrenchMap(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return enhanceAndCountLitPixels(2);
    }

    public long computeResultForPart2() {
        return enhanceAndCountLitPixels(50);
    }

    private int enhanceAndCountLitPixels(int numberOfEnhancements) {
        TrenchImage trenchImage = TrenchImage.parse(input);
        for (int i = 0; i < numberOfEnhancements; i++) {
            trenchImage = trenchImage.enhance();
            LOG.info("After enchancement #{}, map contains {} pixels", i+1, trenchImage.getPixels().size());
        }
        return trenchImage.getPixels().size();
    }

}