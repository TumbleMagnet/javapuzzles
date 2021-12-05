package be.rouget.puzzles.adventofcode.year2016.day16;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class DragonChecksum {

    private static final String INPUT = "01000100010010111";
    private static final int DISK_LENGTH_1 = 272;
    private static final int DISK_LENGTH_2 = 35651584;

    private static final Logger LOG = LogManager.getLogger(DragonChecksum.class);

    public static void main(String[] args) {
        DragonChecksum aoc = new DragonChecksum(INPUT);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public DragonChecksum(String input) {
        LOG.info("Input is {}", input);
    }

    public String computeResultForPart1() {
        return computeDiskChecksum(DISK_LENGTH_1, INPUT);
    }

    public String computeResultForPart2() {
        return computeDiskChecksum(DISK_LENGTH_2, INPUT);
    }

    private String computeDiskChecksum(int diskLength, String data) {
        while (data.length() < diskLength) {
            data = generateWithDragonCurve(data);
        }
        String truncatedData = StringUtils.left(data, diskLength);
        return computeChecksum(truncatedData);
    }

    public static String generateWithDragonCurve(String input) {
        String reversed = new StringBuilder(input).reverse().toString();
        String reversedAndFlipped = flipBits(reversed);
        return input + "0" + reversedAndFlipped;
    }

    private static String flipBits(String input) {
        return AocStringUtils.extractCharacterList(input).stream()
                .map(s -> "0".equals(s) ? "1" : "0")
                .collect(Collectors.joining());
    }

    public static String computeChecksum(String input) {
        if (!hasEvenLength(input)) {
            return input;
        }
        List<List<String>> pairs = Lists.partition(AocStringUtils.extractCharacterList(input), 2);
        String intermediateChecksum = pairs.stream()
                .map(pairList -> pairList.get(0).equals(pairList.get(1)) ? "1" : "0")
                .collect(Collectors.joining());
        if (!hasEvenLength(intermediateChecksum)) {
            return intermediateChecksum;
        }
        return computeChecksum(intermediateChecksum);
    }

    public static boolean hasEvenLength(String input) {
        return input.length() % 2 == 0;
    }
}