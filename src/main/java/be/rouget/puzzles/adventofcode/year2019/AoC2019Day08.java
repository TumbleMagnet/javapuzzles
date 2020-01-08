package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.countMatches;

// Start: 11:10
// Part1: 11:33 (one error)
// Part2: 11:55

public class AoC2019Day08 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day08.class);
    private static int WIDTH = 25;
    private static int HEIGHT = 6;

    public static void main(String[] args) {
        String input = ResourceUtils.readIntoString("aoc_2019_day08_input.txt");
        LOG.info("Part 1 is: " + computeResultPart1(input));
        computeResultPart2(input);
    }

    public static int computeResultPart1(String input) {

        // Split input into layers and find the one with min numbers of 0s
        String bestLayer = splitAndStream(input, WIDTH * HEIGHT)
                .min(Comparator.comparing(s -> countMatches(s, '0')))
                .orElseThrow(() -> new IllegalStateException("Could not find mayer with max count of 0"));
        return countMatches(bestLayer, '1') * countMatches(bestLayer, '2');
    }

    public static void computeResultPart2(String input) {

        // Merge layers and make image printable
        String image = splitAndStream(input, WIDTH * HEIGHT)
                .reduce((top, bottom) -> mergeLayers(top, bottom))
                .orElseThrow(() -> new IllegalStateException("No merged layer!"))
                .replace('0', ' ')
                .replace('1', '\u2588');

        // Print image
        splitAndStream(image, WIDTH).forEach(System.out::println);
    }

    private static String mergeLayers(String topLayer, String bottomLayer) {
        String result = "";
        for (int i=0; i<topLayer.length(); i++) {
            char topChar = topLayer.charAt(i);
            if (topChar == '2') {
                result += bottomLayer.charAt(i);
            }
            else {
                result += topChar;
            }
        }
        return result;
    }

    private static Stream<String> splitAndStream(String input, int size) {
        return Streams.stream(Splitter.fixedLength(size).split(input));
    }
}