package be.rouget.puzzles.adventofcode.year2015.day8;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Matchsticks {

    private static final String YEAR = "2015";
    private static final String DAY = "08";

    private static final Logger LOG = LogManager.getLogger(Matchsticks.class);

    private final List<String> input;

    public Matchsticks(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        Matchsticks aoc = new Matchsticks(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        int codeLength = 0;
        int charLength = 0;
        for (String line : input) {
            codeLength += line.length();
            charLength += charLength(line);
        }

        return codeLength - charLength;
    }

    private int charLength(String line) {
        String result = unescape(line);

        // Remove two chars for leading an trailing ""
        return result.length() -2;
    }

    public static String unescape(String line) {
        String result = "";
        String[] characters = AocStringUtils.splitCharacters(line);
        for (int i = 0; i < characters.length; i++) {
            if ("\\".equals(characters[i])) {
                // Beginning of escape sequence
                i++;
                if ("\\".equals(characters[i])) {
                    result += characters[i];
                } else if ("\"".equals(characters[i])) {
                    result += characters[i];
                } else if ("x".equals(characters[i])) {
                    i += 2;
                    result += "X";
                }
            } else {
                result += characters[i];
            }
        }
        return result;
    }


    public long computeResultForPart2() {
        int codeLength = 0;
        int escapedLength = 0;
        for (String line : input) {
            codeLength += line.length();
            escapedLength += escape(line).length();
        }
        return escapedLength - codeLength;
    }

    public static String escape(String line) {
        String result = "\"";
        for (String character : AocStringUtils.extractCharacterList(line)) {
            if ("\"".equals(character)) {
                result += "\\\"";
            } else if ("\\".equals(character)) {
                result += "\\\\";
            }
            else {
                result += character;
            }
        }
        result += "\"";
        return result;
    }

}