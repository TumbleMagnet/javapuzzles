package be.rouget.puzzles.adventofcode.year2016.day18;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.year2016.day18.Tile.SAFE;
import static be.rouget.puzzles.adventofcode.year2016.day18.Tile.TRAP;

public class LikeARogue {

    private static final Logger LOG = LogManager.getLogger(LikeARogue.class);
    private static final String INPUT_LINE = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^.";
    public static final int NUMBER_OF_ROWS_PART1 = 40;
    public static final int NUMBER_OF_ROWS_PART2 = 400000;

    private final String inputLine;
    private final int numberOfRowsPart1;
    private final int numberOfRowsPart2;

    public static void main(String[] args) {
        LikeARogue aoc = new LikeARogue(INPUT_LINE, NUMBER_OF_ROWS_PART1, NUMBER_OF_ROWS_PART2);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public LikeARogue(String inputLine, int numberOfRowsPart1, int numberOfRowsPart2) {
        this.inputLine = inputLine;
        this.numberOfRowsPart1 = numberOfRowsPart1;
        this.numberOfRowsPart2 = numberOfRowsPart2;
        LOG.info("Input line is: {}", this.inputLine);
        LOG.info("Number of rows: {}, {}", this.numberOfRowsPart1, this.numberOfRowsPart2);
    }

    public long computeResultForPart1() {
        return countSafeTilesLineByLine(this.numberOfRowsPart1);
    }

    public long computeResultForPart2() {
        return countSafeTilesLineByLine(this.numberOfRowsPart2);
    }

    private long countSafeTilesLineByLine(int numberOfRows) {
        List<Tile> line = AocStringUtils.extractCharacterList(inputLine).stream()
                .map(Tile::fromMapChar)
                .collect(Collectors.toList());
        long safeCount = countSafeTiles(line);
        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
            line = nextLine(line);
            safeCount += countSafeTiles(line);
        }
        return safeCount;
    }

    private List<Tile> nextLine(List<Tile> previousLine) {
        List<Tile> nextLine = Lists.newArrayList();
        for (int x = 0; x < previousLine.size(); x++) {
            Tile left = x -1 >= 0 ? previousLine.get(x-1) : SAFE;
            Tile center = previousLine.get(x);
            Tile right = x + 1 < previousLine.size() ? previousLine.get(x+1) : SAFE;
            nextLine.add(computeNextTile(left, center, right));
        }
        return nextLine;
    }

    private long countSafeTiles(List<Tile> line) {
        return line.stream()
                .filter(entry -> entry == SAFE)
                .count();
    }

    public static Tile computeNextTile(Tile left, Tile center, Tile right) {
        if (left == TRAP && center == TRAP && right == SAFE) {
            return TRAP;
        }
        if (left == SAFE && center == TRAP && right == TRAP) {
            return TRAP;
        }
        if (left == TRAP && center == SAFE && right == SAFE) {
            return TRAP;
        }
        if (left == SAFE && center == SAFE && right == TRAP) {
            return TRAP;
        }
        return SAFE;
    }

}