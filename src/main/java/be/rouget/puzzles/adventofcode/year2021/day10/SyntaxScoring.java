package be.rouget.puzzles.adventofcode.year2021.day10;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.util.AocStringUtils.extractCharacterList;

public class SyntaxScoring {

    private static final String YEAR = "2021";
    private static final String DAY = "10";

    private static final Logger LOG = LogManager.getLogger(SyntaxScoring.class);
    private final List<ProgramLine> lines;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SyntaxScoring aoc = new SyntaxScoring(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SyntaxScoring(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        lines = input.stream()
                .map(ProgramLine::new)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return lines.stream()
                .filter(ProgramLine::isCorrupted)
                .map(ProgramLine::getFirstInvalidChar)
                .mapToLong(this::scoreSyntaxError)
                .sum();
    }

    public long computeResultForPart2() {
        List<Long> sortedCompletionScores = lines.stream()
                .filter(line -> !line.isCorrupted())
                .map(ProgramLine::autoComplete)
                .map(this::scoreCompletion)
                .sorted()
                .collect(Collectors.toList());
        return sortedCompletionScores.get(sortedCompletionScores.size() / 2);
    }

    private long scoreSyntaxError(String s) {
        switch (s) {
            case ")": return 3;
            case "]": return 57;
            case "}": return 1197;
            case ">": return 25137;
            default:
                throw new IllegalArgumentException("Invalid closing character: " + s);
        }
    }

    private long scoreCompletion(String completion) {
        long score = 0;
        for (String c : extractCharacterList(completion)) {
            score = scoreCompletionChar(score, c);
        }
        return score;
    }

    private long scoreCompletionChar(long score, String c) {
        switch (c) {
            case ")": return score * 5 + 1;
            case "]": return score * 5 + 2;
            case "}": return score * 5 + 3;
            case ">": return score * 5 + 4;
            default:
                throw new IllegalArgumentException("Invalid closing character: " + c);
        }
    }
}