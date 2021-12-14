package be.rouget.puzzles.adventofcode.year2021.day8;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SevenSegmentSearch {

    private static final String YEAR = "2021";
    private static final String DAY = "08";

    private static final Logger LOG = LogManager.getLogger(SevenSegmentSearch.class);
    private final List<Display> displays;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SevenSegmentSearch aoc = new SevenSegmentSearch(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SevenSegmentSearch(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        displays = input.stream()
                .map(Display::fromInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return displays.stream()
                .map(Display::getOutputDigits)
                .flatMap(List::stream)
                .filter(digit -> digit.length() == Digit.ONE.getNumberOfSegments()
                       || digit.length() == Digit.FOUR.getNumberOfSegments()
                       || digit.length() == Digit.SEVEN.getNumberOfSegments()
                       || digit.length() == Digit.EIGHT.getNumberOfSegments())
                .count();
    }


    public long computeResultForPart2() {
        return displays.stream()
                .map(Display::getDecodedOutput)
                .mapToLong(Long::parseLong)
                .sum();
    }
}