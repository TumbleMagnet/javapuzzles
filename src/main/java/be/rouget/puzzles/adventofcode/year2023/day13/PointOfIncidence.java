package be.rouget.puzzles.adventofcode.year2023.day13;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static be.rouget.puzzles.adventofcode.year2023.day13.GroundTile.ASH;
import static be.rouget.puzzles.adventofcode.year2023.day13.GroundTile.ROCK;


public class PointOfIncidence {
    private static final Logger LOG = LogManager.getLogger(PointOfIncidence.class);

    private final List<RectangleMap<GroundTile>> maps;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PointOfIncidence.class);
        PointOfIncidence aoc = new PointOfIncidence(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PointOfIncidence(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        List<List<String>> mapInputs = splitBySeparator(input, StringUtils::isBlank);
        maps = mapInputs.stream()
                .map(mapInput -> new RectangleMap<>(mapInput, GroundTile::parse))
                .toList();
        LOG.info("Found {} maps...", maps.size());
    }

    private static <T> List<List<T>> splitBySeparator(List<T> list, Predicate<? super T> predicate) {
        final List<List<T>> finalList = new ArrayList<>();
        int fromIndex = 0;
        int toIndex = 0;
        for (T elem : list) {
            if (predicate.test(elem)) {
                finalList.add(list.subList(fromIndex, toIndex));
                fromIndex = toIndex + 1;
            }
            toIndex++;
        }
        if (fromIndex != toIndex) {
            finalList.add(list.subList(fromIndex, toIndex));
        }
        return finalList;
    }

    public long computeResultForPart1() {
        return maps.stream()
                .mapToLong(PointOfIncidence::reflectionSummaryIndexPart1)
                .sum();
    }

    private static long reflectionSummaryIndexPart1(RectangleMap<GroundTile> map) {
        long index = computeReflectionIndex(map, 0);
        if (index == 0) {
            throw new IllegalStateException("Did not find a reflection index for part 1!");
        }
        return index;
    }

    private static long computeReflectionIndex(RectangleMap<GroundTile> map, long skipIndex) {
        for (int x = 0; x < map.getWidth() - 1; x++) {
            if (hasVerticalReflectionLine(map, x)) {
                long index = x + 1L;
                if (index != skipIndex) {
                    return index;
                }
            }
        }
        for (int y = 0; y < map.getHeight() - 1; y++) {
            if (hasHorizontalReflectionLine(map, y)) {
                long index = (y + 1L) * 100L;
                if (index != skipIndex) {
                    return index;
                }
            }
        }
        // Not found
        return 0;
    }

    private static boolean hasVerticalReflectionLine(RectangleMap<GroundTile> map, int xBefore) {
        int sizeBefore = xBefore + 1;
        int sizeAfter = map.getWidth() - sizeBefore;
        int size = Math.min(sizeBefore, sizeAfter);
        for (int y = 0; y < map.getHeight(); y++) {
            for (int i = 0; i < size; i++) {
                Position positionAfter = new Position(xBefore + 1 + i, y);
                Position positionBefore = new Position(xBefore - i, y);
                if (map.getElementAt(positionBefore) != map.getElementAt(positionAfter)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasHorizontalReflectionLine(RectangleMap<GroundTile> map, int yBefore) {
        int sizeBefore = yBefore + 1;
        int sizeAfter = map.getHeight() - sizeBefore;
        int size = Math.min(sizeBefore, sizeAfter);
        for (int x = 0; x < map.getWidth(); x++) {
            for (int i = 0; i < size; i++) {
                Position positionAfter = new Position(x, yBefore + 1 + i);
                Position positionBefore = new Position(x, yBefore - i);
                if (map.getElementAt(positionBefore) != map.getElementAt(positionAfter)) {
                    return false;
                }
            }
        }
        return true;
    }

    public long computeResultForPart2() {
        return maps.stream()
                .mapToLong(PointOfIncidence::reflectionSummaryIndexPart2)
                .sum();
    }

    private static long reflectionSummaryIndexPart2(RectangleMap<GroundTile> map) {
        long indexBefore = computeReflectionIndex(map, 0);
        
        // Switch a tile unit we get a different reflection index, return it
        for (int x = 0; x < map.getWidth() ; x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Position pos = new Position(x, y);
                switchElementAt(map, pos);
                long indexAfter = computeReflectionIndex(map, indexBefore);
                switchElementAt(map, pos);
                if (indexAfter != 0 && indexAfter != indexBefore) {
                    return indexAfter;
                }
            }
        }
        throw new IllegalStateException("Did not find a different reflection line for part 2!");
    }

    private static void switchElementAt(RectangleMap<GroundTile> map, Position position) {
        GroundTile elementBefore = map.getElementAt(position);
        GroundTile elementAfter = elementBefore == ASH ? ROCK : ASH;
        map.setElementAt(position, elementAfter);
    }

}