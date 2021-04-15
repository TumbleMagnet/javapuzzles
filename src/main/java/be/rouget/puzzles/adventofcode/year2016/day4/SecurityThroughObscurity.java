package be.rouget.puzzles.adventofcode.year2016.day4;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SecurityThroughObscurity {

    private static final String YEAR = "2016";
    private static final String DAY = "04";

    private static final Logger LOG = LogManager.getLogger(SecurityThroughObscurity.class);
    private final List<Room> rooms;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SecurityThroughObscurity aoc = new SecurityThroughObscurity(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SecurityThroughObscurity(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        rooms = input.stream()
                .map(Room::fromInput)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return rooms.stream()
                .filter(Room::isValid)
                .mapToInt(Room::getSectorId)
                .sum();
    }

    public long computeResultForPart2() {
        return rooms.stream()
                .filter(Room::isValid)
                .filter(room -> room.decryptName().contains("pole"))
                .findFirst().orElseThrow()
                .getSectorId();
    }
}