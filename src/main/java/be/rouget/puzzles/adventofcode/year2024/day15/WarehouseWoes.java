package be.rouget.puzzles.adventofcode.year2024.day15;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static be.rouget.puzzles.adventofcode.util.AocStringUtils.extractCharacterList;
import static be.rouget.puzzles.adventofcode.util.AocStringUtils.join;


public class WarehouseWoes {

    private static final Logger LOG = LogManager.getLogger(WarehouseWoes.class);
    private final RectangleMap<WarehouseItem> startingMap;
    private final List<Command> commands;

    @SuppressWarnings("java:S2629") // OK to compute results when logging
    static void main() {
        List<String> input = SolverUtils.readInput(WarehouseWoes.class);
        WarehouseWoes aoc = new WarehouseWoes(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public WarehouseWoes(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        // Extract lines from the map and parse it
        List<String> mapLines = input.stream()
                .filter(line -> line.startsWith("#"))
                .toList();
        startingMap = new RectangleMap<>(mapLines, WarehouseItem::parse);
        LOG.info("Parsed map with width {} and height {}", startingMap.getWidth(), startingMap.getHeight());

        // Extract command lines and marge them into a list of commands
        List<String> commandLines = input.stream()
                .filter(line -> !line.startsWith("#") && StringUtils.isNotBlank(line))
                .toList();
        commands = extractCharacterList(join(commandLines)).stream()
                .map(Command::parse)
                .toList();
        LOG.info("Found {} commands", commands.size());
    }

    public long computeResultForPart1() {
        Warehouse warehouse = new Warehouse(startingMap);
        commands.forEach(warehouse::moveRobot);
        return warehouse.computeSumOfBoxGpsCoordinates();
    }

    public long computeResultForPart2() {
        WideWarehouse warehouse = new WideWarehouse(startingMap);
        commands.forEach(warehouse::moveRobot);
        return warehouse.computeSumOfBoxGpsCoordinates();
    }
}