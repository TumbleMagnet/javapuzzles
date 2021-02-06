package be.rouget.puzzles.adventofcode.year2015.day6;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class FireHazard {

    private static final String YEAR = "2015";
    private static final String DAY = "06";

    private static final Logger LOG = LogManager.getLogger(FireHazard.class);

    private final List<String> input;

    public FireHazard(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        FireHazard aoc = new FireHazard(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        LightGrid grid = new LightGrid();
        input.stream()
                .map(Command::fromInput)
                .forEach(grid::runCommand);
        return grid.countLitLights();
    }

    public long computeResultForPart2() {
        LightGridWithBrightness grid = new LightGridWithBrightness();
        input.stream()
                .map(Command::fromInput)
                .forEach(grid::runCommand);
        return grid.countBrightness();

    }
}