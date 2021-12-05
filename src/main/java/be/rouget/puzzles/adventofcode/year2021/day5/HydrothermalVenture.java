package be.rouget.puzzles.adventofcode.year2021.day5;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HydrothermalVenture {

    private static final String YEAR = "2021";
    private static final String DAY = "05";

    private static final Logger LOG = LogManager.getLogger(HydrothermalVenture.class);
    private final List<VentLine> ventLines;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        HydrothermalVenture aoc = new HydrothermalVenture(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public HydrothermalVenture(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        ventLines = input.stream()
                .map(VentLine::fromInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return ventLines.stream()
                .filter(line -> line.isVertical() || line.isHorizontal())
                .map(VentLine::toPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(2L) >= 0)
                .count();
    }

    public long computeResultForPart2() {
        return ventLines.stream()
                .map(VentLine::toPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(2L) >= 0)
                .count();
    }
}