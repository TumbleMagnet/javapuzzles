package be.rouget.puzzles.adventofcode.year2020.day17;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ConwayCubes {

    private static final String YEAR = "2020";
    private static final String DAY = "17";

    private static final Logger LOG = LogManager.getLogger(ConwayCubes.class);

    private final List<String> input;
    private final RectangleMap<ActiveStatus> startingMap;

    public ConwayCubes(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        startingMap = new RectangleMap<>(input, ActiveStatus::fromMapChar);
        LOG.info("Staring map: \n{}", startingMap);
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ConwayCubes aoc = new ConwayCubes(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        // Build initial cube from Map
        CubeMap cubeMap = new CubeMap(startingMap.getWidth(), startingMap.getHeight(), 1);
        for (int x = 0; x < startingMap.getWidth(); x++) {
            for (int y = 0; y < startingMap.getHeight(); y++) {
                cubeMap.setStatusAt(new Coordinates(x, y, 0), startingMap.getElementAt(new Position(x, y)));
            }
        }
        LOG.info("Initial map: \n{}", cubeMap);

        // Do 6 cycles
        for (int cycle = 0; cycle < 6; cycle++) {
            cubeMap = cubeMap.cycle();
            // LOG.info("Map after cycle {}: \n{}", cycle, cubeMap);
        }

        // Count active elements
        return cubeMap.countActiveCubes();
    }

    public long computeResultForPart2() {
        // Build initial cube from Map
        HyperCubeMap cubeMap = new HyperCubeMap(startingMap.getWidth(), startingMap.getHeight(), 1, 1);
        for (int x = 0; x < startingMap.getWidth(); x++) {
            for (int y = 0; y < startingMap.getHeight(); y++) {
                cubeMap.setStatusAt(new HyperCoordinates(x, y, 0, 0), startingMap.getElementAt(new Position(x, y)));
            }
        }
        // LOG.info("Initial map: \n{}", cubeMap);

        // Do 6 cycles
        for (int cycle = 0; cycle < 6; cycle++) {
            cubeMap = cubeMap.cycle();
            // LOG.info("Map after cycle {}: \n{}", cycle, cubeMap);
        }

        // Count active elements
        return cubeMap.countActiveCubes();
    }
}