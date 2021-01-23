package be.rouget.puzzles.adventofcode.year2020.day23;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CrabCups {

    private static final String YEAR = "2020";
    private static final String DAY = "23";

    private static final Logger LOG = LogManager.getLogger(CrabCups.class);

    private final List<String> input;

    public CrabCups(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = List.of("562893147");
        CrabCups aoc = new CrabCups(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        CupGame game = CupGame.fromInput(input.get(0));
        for (int i = 0; i < 100; i++) {
            game.doAMove();
        }
        return Long.parseLong(game.printLabelsAfter1());
    }

    public long computeResultForPart2() {

        CupGame game = CupGame.fromInput(input.get(0), 1000000);
        for (long i = 0; i < 10000000L; i++) {
            game.doAMove();
        }
        return game.getStarProduct();
    }
}