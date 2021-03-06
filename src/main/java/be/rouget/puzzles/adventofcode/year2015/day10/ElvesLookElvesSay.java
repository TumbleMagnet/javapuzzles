package be.rouget.puzzles.adventofcode.year2015.day10;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ElvesLookElvesSay {

    private static final String YEAR = "2015";
    private static final String DAY = "10";

    private static final Logger LOG = LogManager.getLogger(ElvesLookElvesSay.class);

    private final List<String> input;

    public ElvesLookElvesSay(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = List.of("1113222113");
        ElvesLookElvesSay aoc = new ElvesLookElvesSay(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        String sentence = input.get(0);
        return lengthAfterLookAndSay(sentence, 40);
    }

    private int lengthAfterLookAndSay(String sentence, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            sentence = lookAndSay(sentence);
        }
        return sentence.length();
    }

    public long computeResultForPart2() {
        String sentence = input.get(0);
        return lengthAfterLookAndSay(sentence, 50);
    }

    public static String lookAndSay(String input) {

        StringBuffer result = new StringBuffer();
        String previousChar = null;
        int count = 0;
        for (String character : AocStringUtils.splitCharacters(input)) {
            if (previousChar == null) {
                // Start of string
                previousChar = character;
                count = 1;
            } else if (previousChar.equals(character)) {
                // Same as previous character, keep counting
                count++;
            } else {
                // Different character
                result.append(count);
                result.append(previousChar);
                previousChar = character;
                count = 1;
            }
        }
        result.append(count);
        result.append(previousChar);
        return result.toString();
    }
}