package be.rouget.puzzles.adventofcode.year2024.day04;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;


public class CeresSearch {

    private static final Logger LOG = LogManager.getLogger(CeresSearch.class);
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CeresSearch.class);
        CeresSearch aoc = new CeresSearch(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CeresSearch(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {

        List<String> lines = Lists.newArrayList();

        // Horizontal lines
        lines.addAll(input);

        // Vertical lines
        lines.addAll(extractVerticalLines());

        // Diagonals
        lines.addAll(extractDiagonals());

        return lines.stream()
                .mapToLong(CeresSearch::countXmas)
                .sum();
    }

    private Collection<String> extractDiagonals() {

        int numberOfLines = input.size();
        int numberOfColumns = input.getFirst().length();

        if (numberOfColumns != numberOfLines) {
            throw new IllegalStateException("Expected square but got " + numberOfLines + " lines and " + numberOfColumns + " columns");
        }
        int size = numberOfLines;

        List<String> diagonals = Lists.newArrayList();

        // Diagonals in the SW directions:
        // - start from positions (0,0) to (2n-2, 0)
        // - for each, draw a line going south-west and of length n
        // - keep only elements in the square
        for (int x = 0; x < 2* size -1 ; x++) {
            StringBuilder diagonal = new StringBuilder();
            for (int i = 0; i < size; i++) {
                Position p = new Position(x - i, i);
                if (isInSquare(p, size)) {
                    diagonal.append(getCharAtPosition(p));
                }
            }
            diagonals.add(diagonal.toString());
        }

        // Diagonals in the south-east directions
        // - start from positions (1-n, 0) to (n-1, 0)
        // - for each, draw a line going south-east and of length n
        // - keep only positions of that line that are in the square
        for (int x = 1-size; x < size; x++) {
            StringBuilder diagonal = new StringBuilder();
            for (int i = 0; i < size; i++) {
                Position p = new Position(x + i, i);
                if (isInSquare(p, size)) {
                    diagonal.append(getCharAtPosition(p));
                }
            }
            diagonals.add(diagonal.toString());
        }

        return diagonals;
    }

    private List<String> extractVerticalLines() {
        List<String> lines = Lists.newArrayList();
        int lineSize = input.getFirst().length();
        for (int columnIndex = 0; columnIndex < lineSize; columnIndex++) {
            StringBuilder sb = new StringBuilder();
            for (int lineIndex = 0; lineIndex < input.size(); lineIndex++) {
                sb.append(getCharAt(lineIndex, columnIndex));
            }
            lines.add(sb.toString());
        }
        return lines;
    }

    private char getCharAtPosition(Position position) {
        return getCharAt(position.getX(), position.getY());
    }

    private char getCharAt(int lineIndex, int columnIndex) {
        return input.get(lineIndex).charAt(columnIndex);
    }

    private boolean isInSquare(Position position, int size) {
        return (position.getX() >= 0 && position.getX() < size)
                && (position.getY() >=0 && position.getY() < size);
    }

    public long computeResultForPart2() {
        return -1;
    }

    public static long countXmas(String line) {
        String xmas = "XMAS";
        String samx = "SAMX";
        long count = 0;
        for (int i = 0; i <= line.length() - 4; i++) {
            String token = line.substring(i, i + 4);
            if (xmas.equals(token) || samx.equals(token)) {
                count++;
            }
        }
        return count;
    }


}