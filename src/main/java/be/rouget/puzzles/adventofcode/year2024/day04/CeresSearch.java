package be.rouget.puzzles.adventofcode.year2024.day04;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.MapCharacter;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CeresSearch {

    private static final Logger LOG = LogManager.getLogger(CeresSearch.class);
    private final List<String> input;
    private final RectangleMap<XmasChar> instructions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CeresSearch.class);
        CeresSearch aoc = new CeresSearch(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CeresSearch(List<String> input) {
        this.input = input;
        this.instructions = new RectangleMap<>(this.input, XmasChar::new);
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

    private List<String> extractVerticalLines() {
        List<String> lines = Lists.newArrayList();
        int lineSize = instructions.getWidth();
        for (int x = 0; x < lineSize; x++) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < instructions.getHeight(); y++) {
                sb.append(getCharAtPosition(new Position(x, y)));
            }
            lines.add(sb.toString());
        }
        return lines;
    }

    private Collection<String> extractDiagonals() {

        int numberOfLines = instructions.getHeight();
        int numberOfColumns = instructions.getWidth();

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
                if (instructions.isPositionInMap(p)) {
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
                if (instructions.isPositionInMap(p)) {
                    diagonal.append(getCharAtPosition(p));
                }
            }
            diagonals.add(diagonal.toString());
        }

        return diagonals;
    }

    private String getCharAtPosition(Position position) {
        return instructions.getElementAt(position).getMapChar();
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

    public long computeResultForPart2() {
        return instructions.getElements().stream()
                .filter(entry -> isCenterOfXmas(instructions, entry))
                .count();
    }

    private boolean isCenterOfXmas(RectangleMap<XmasChar> instructions, Map.Entry<Position, XmasChar> entry) {

        // Current letter must be A
        if (!"A".equals(entry.getValue().getMapChar())) {
            return false;
        }

        // Current position cannot be on an edge
        Position position = entry.getKey();
        int x = position.getX();
        int y = position.getY();
        if (x == 0 || x == instructions.getWidth() - 1) {
            return false;
        }
        if (y == 0 || y == instructions.getHeight() -1) {
            return false;
        }

        // Check for diagonal X-MAS
        Set<String> lettersOnFirstDiagonal = Stream.of(new Position(x - 1, y - 1), new Position(x + 1, y + 1))
                .map(this::getCharAtPosition)
                .collect(Collectors.toSet());
        Set<String> lettersOnSecondDiagonal = Stream.of(new Position(x + 1, y - 1), new Position(x - 1, y + 1))
                .map(this::getCharAtPosition)
                .collect(Collectors.toSet());
        return canFormXmas(lettersOnFirstDiagonal) && canFormXmas(lettersOnSecondDiagonal);
    }

    private static boolean canFormXmas(Set<String> letters) {
        return letters.size() == 2 && letters.contains("M") && letters.contains("S");
    }

    public record XmasChar(String characterString) implements MapCharacter {
        @Override
        public String getMapChar() {
            return characterString;
        }
    }

}