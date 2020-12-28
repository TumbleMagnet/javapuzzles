package be.rouget.puzzles.adventofcode.year2020.day11;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;

public class SeatingSystem {

    private static final String YEAR = "2020";
    private static final String DAY = "11";

    private static final Logger LOG = LogManager.getLogger(SeatingSystem.class);

    private final List<String> input;

    public SeatingSystem(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SeatingSystem aoc = new SeatingSystem(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return computeResult(seatingMap -> seatingMap.predictNextMap());
    }

    public long computeResultForPart2() {
        return computeResult(seatingMap -> seatingMap.predictNextMap2());
    }

    public long computeResult(Function<SeatingMap, SeatingMap> transformer) {
        SeatingMap seatingMap = new SeatingMap(input);
        LOG.info("Seating map is {} x {}", seatingMap.getWidth(), seatingMap.getHeight());

        int count = 1;
        while (true) {
            LOG.info("Iteration {}...", count);
            SeatingMap newMap = transformer.apply(seatingMap);
            if (newMap.equals(seatingMap)) {
                return seatingMap.countOccupiedSeats();
            }
            seatingMap = newMap;
            count++;
        }
    }
}