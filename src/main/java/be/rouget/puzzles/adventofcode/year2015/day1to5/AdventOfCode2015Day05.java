package be.rouget.puzzles.adventofcode.year2015.day1to5;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AdventOfCode2015Day05 {

    private static Logger LOG = LogManager.getLogger(AdventOfCode2015Day05.class);
    private static final char[] vowels = new char[] {'a','e','i','o','u'};
    private static final String[] forbiddenStrings = new String[] {"ab", "cd", "pq", "xy"};

    public static int countNiceStrings(List<String> input) {
        int niceCount = 0;
        for (String s: input) {
            if (isNice(s)) {
                LOG.info(s + " is nice");
                niceCount++;
            }
            else {
                LOG.info(s + " is not nice");
            }
        }
        return niceCount;
    }

    public static boolean isNice(String input) {

        // There must be at least three vowels
        int vowelCount = 0;
        for (char vowel: vowels) {
            vowelCount += StringUtils.countMatches(input, vowel);
        }
        if (vowelCount < 3) {
            return false;
        }

        // It must contain the same character twice in a row
        Character previousChar = null;
        boolean foundCharTwiceInARow = false;
        for (char c : input.toCharArray()) {
            if (previousChar == null) {
                previousChar = c;
            }
            else {
                if (previousChar.equals(c)) {
                    foundCharTwiceInARow = true;
                    break;
                }
                previousChar = c;
            }
        }
        if (!foundCharTwiceInARow) {
            return false;
        }

        // It cannot contain forbidden strings
        for (String forbiddenString : forbiddenStrings) {
            if (input.contains(forbiddenString)) {
                return false;
            }
        }

        return true;
    }

    public static long countNiceStrings2(List<String> input) {
        return input.stream()
                .peek(s -> LOG.info("Is nice2 {}: {}", s, isNice2(s)))
                .filter(s -> isNice2(s))
                .count();
    }

    public static boolean isNice2(String input) {

        // It contains a pair of any two letters that appears at least twice in the string without overlapping,
        // like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
        if (!foundDuplicatedNonOverlappingPair(input)) {
            return false;
        }

        // It contains at least one letter which repeats with exactly one letter between them,
        // like xyx, abcdefeghi (efe), or even aaa.
        for (int i = 0; i < input.length()-2; i++) {
            if (input.charAt(i) == input.charAt(i+2)) {
                return true;
            }
        }

        return false;
    }

    private static boolean foundDuplicatedNonOverlappingPair(String input) {
        String previousCharacter = null;
        Map<String, Integer> pairs = Maps.newHashMap();
        int index = 0;
        for (String character: AocStringUtils.extractCharacterList(input)) {
            if (previousCharacter != null) {
                String currentPair = previousCharacter + character;
                if (pairs.containsKey(currentPair)) {
                    Integer previousLocationOfPair = pairs.get(currentPair);
                    if (index > previousLocationOfPair + 1 ) {
                        return true;
                    }
                    // Keep previous pair in map
                } else {
                    pairs.put(currentPair, index);
                }
            }
            index++;
            previousCharacter = character;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Starting puzzle...");
        List<String> input = ResourceUtils.readLines("aoc_2015_day05_input.txt");
        int niceCount = AdventOfCode2015Day05.countNiceStrings(input);
        LOG.info("Number of nice strings (1): " + niceCount);
        LOG.info("Number of nice strings (2):  " +  AdventOfCode2015Day05.countNiceStrings2(input));
    }
}
