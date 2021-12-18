package be.rouget.puzzles.adventofcode.year2021.day11;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class DumboOctopus {

    private static final String YEAR = "2021";
    private static final String DAY = "11";

    private static final Logger LOG = LogManager.getLogger(DumboOctopus.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        DumboOctopus aoc = new DumboOctopus(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public DumboOctopus(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.input = input;
    }

    public long computeResultForPart1() {
        RectangleMap<OctopusChar> grid =  new RectangleMap<>(this.input, OctopusChar::fromInputCharacter);
        int numberOfFlashes = 0;
        for (int i = 0; i < 100; i++) {
            numberOfFlashes += step(grid);
        }
        return numberOfFlashes;
    }

    public long computeResultForPart2() {
        RectangleMap<OctopusChar> grid =  new RectangleMap<>(this.input, OctopusChar::fromInputCharacter);
        int step = 1;
        while (true) {
            if (step(grid) == grid.getWidth() * grid.getHeight()) {
                return step;
            }
            step++;
        }
    }

    public int step(RectangleMap<OctopusChar> grid) {

        int numberOfFlashes = 0;

        // Increase energy levels by 1
        grid.getElements().forEach(entry -> entry.getValue().increaseLevel());

        // Find octopuses to flash
        boolean keepGoing = true;
        while (keepGoing) {
            // Find an octopus to flash
            Map.Entry<Position, OctopusChar> octopusToFlash = grid.getElements().stream()
                    .filter(entry -> entry.getValue().isReadyToFlash())
                    .findFirst().orElse(null);
            if (octopusToFlash == null) {
                keepGoing = false;
            }
            else {
                // Flash the octopus
                octopusToFlash.getValue().flash();
                numberOfFlashes++;

                // Increase levels of neighbours
                Position flashPosition = octopusToFlash.getKey();
                grid.enumerateNeighbourPositions(flashPosition)
                        .forEach(neighbour -> grid.getElementAt(neighbour).increaseLevelIfNotFlashedThisStep());
            }
        }

        return numberOfFlashes;
    }
}