package be.rouget.puzzles.adventofcode.year2020.day6;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CustomCustoms {

    private static final String YEAR = "2020";
    private static final String DAY = "06";

    private static Logger LOG = LogManager.getLogger(CustomCustoms.class);

    private List<String> input;

    public CustomCustoms(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        CustomCustoms aoc = new CustomCustoms(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        // Answers for a group are union of answers for ech person
        Set<String> combinedAnswers = Sets.newHashSet();
        long total = 0;
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                total += combinedAnswers.size();
                combinedAnswers.clear();
            } else {
                List<String> collection = extractCharacters(line);
                combinedAnswers.addAll(collection);
            }
        }
        total += combinedAnswers.size();
        return total;
    }

    public long computeResultForPart2() {
        // Answers for a group are intersection of answers for ech person
        Set<String> combinedAnswers = null;
        long total = 0;
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                total += combinedAnswers.size();
                combinedAnswers = null;
            } else {
                if (combinedAnswers == null) {
                    combinedAnswers = Sets.newHashSet(extractCharacters(line));
                } else {
                    combinedAnswers.retainAll(Sets.newHashSet(extractCharacters(line)));
                }
            }
        }
        total += combinedAnswers.size();
        return total;
    }

    private List<String> extractCharacters(String line) {
        String[] lineChars = line.split("(?!^)");
        List<String> collection = Arrays.asList(lineChars);
        return collection;
    }

}