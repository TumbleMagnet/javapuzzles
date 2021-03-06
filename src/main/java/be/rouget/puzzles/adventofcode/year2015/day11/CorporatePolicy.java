package be.rouget.puzzles.adventofcode.year2015.day11;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorporatePolicy {

    private static final String YEAR = "2020";
    private static final String DAY = "11";

    private static final Logger LOG = LogManager.getLogger(CorporatePolicy.class);

    private final List<String> input;
    private String nextPassword;

    public CorporatePolicy(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = List.of("vzbxkghb");
        CorporatePolicy aoc = new CorporatePolicy(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public String computeResultForPart1() {
        String input = this.input.get(0);
        nextPassword = computeNextPassword(input);
        return nextPassword;
    }

    private String computeNextPassword(String input) {
        String password = increment(input);
        while (!isValidPassword(password)) {
            password = increment(password);
        }
        return password;
    }

    public String computeResultForPart2() {
        return computeNextPassword(nextPassword);
    }

    public static boolean isValidPassword(String password) {

        // Passwords must be exactly eight lowercase letters
        Pattern passwordPattern = Pattern.compile("[a-z]{8}");
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            return false;
        }

        // Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz.
        // They cannot skip letters; abd doesn't count.
        if (!containsStraightOfThreeIncreasingCharacters(password)) {
            return false;
        }

        // Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
        if (password.contains("i") || password.contains("o") || password.contains("l")) {
            return false;
        }

        // Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
        return containsTwoDifferentNonOverlappingPairs(password);
    }

    public static boolean containsTwoDifferentNonOverlappingPairs(String input) {
        if (StringUtils.isBlank(input) || input.length() < 4) {
            return false;
        }
        int countPairs = 0;
        for (int i = 0; i < input.length()-1; i++) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                countPairs++;
                i++; // In order to count only *non-overlapping* pairs, skip second character of pair
            }
        }
        return countPairs >= 2;
    }

    public static boolean containsStraightOfThreeIncreasingCharacters(String input) {
        if (StringUtils.isBlank(input) || input.length() < 3) {
            return false;
        }
        for (int i = 0; i < input.length()-2; i++) {
            int char1 = input.charAt(i);
            int char2 = input.charAt(i+1);
            int char3 = input.charAt(i+2);
            if ((char2 == char1 + 1) && (char3 == char2 + 1)) {
                return true;
            }
        }
        return false;
    }

    public static String increment(String input) {

        String result = input;
        boolean keepIncrementing = true;
        int index = input.length()-1;

        while (keepIncrementing) {
            if (index < 0) {
                throw new IllegalStateException("Overflow!");
            }
            char newLetter = incrementLetter(input.charAt(index));
            result = changeLetterAt(result, newLetter, index);
            keepIncrementing = newLetter == 'a';
            index--;
        }
        return result;
    }

    public static String changeLetterAt(String input, char newLetter, int index) {
        StringBuilder builder = new StringBuilder(input);
        builder.setCharAt(index, newLetter);
        return builder.toString();
    }

    public static char incrementLetter(char inputChar) {
        if (inputChar == 'z') {
            return 'a';
        }
        int charValue = inputChar;
        return (char) (charValue + 1);
    }
}