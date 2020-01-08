package be.rouget.puzzles.adventofcode.year2015;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AdventOfCode2015Day05 {

    private static Logger LOG = LogManager.getLogger(AdventOfCode2015Day03.class);
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



    public static void main(String[] args) {
        System.out.println("Starting puzzle...");
        List<String> input = ResourceUtils.readLines("aoc_2015_day05_input.txt");
        int niceCount = AdventOfCode2015Day05.countNiceStrings(input);
        LOG.info("Number of nice strings: " + niceCount);
    }
}
