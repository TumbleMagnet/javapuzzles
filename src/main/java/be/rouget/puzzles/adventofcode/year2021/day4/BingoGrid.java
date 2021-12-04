package be.rouget.puzzles.adventofcode.year2021.day4;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGrid {
    private final static int WIDTH = 5;
    private final List<Integer> draws = Lists.newArrayList();
    private final Map<Position, Integer> grid = Maps.newHashMap();

    public BingoGrid(List<String> lines) {
        if (lines.size() != 5) {
            throw new IllegalArgumentException("Expected " + WIDTH + " lines, got " + lines.size());
        }
        for (int y = 0; y < WIDTH; y++) {
            String currentLine = lines.get(y);
            List<Integer> values = parseLine(currentLine);
            if (values.size() != WIDTH) {
                throw new IllegalArgumentException("Expected " + WIDTH + " elements, got " + values.size() + " for line " + currentLine);
            }
            for (int x = 0; x < WIDTH; x++) {
                Position position = new Position(x, y);
                grid.put(position, values.get(x));
            }
        }
    }

    public void reset() {
        this.draws.clear();
    }

    public static List<Integer> parseLine(String line) {
        return Arrays.stream(line.split(" "))
                .filter(StringUtils::isNotBlank)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public boolean addDraw(Integer draw) {
        draws.add(draw);
        return wins();
    }

    private boolean wins() {
        for (int i = 0; i < WIDTH; i++) {
            if (draws.containsAll(getRow(i)) || draws.containsAll(getColumn(i))) {
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        int lastDraw = draws.get(draws.size()-1);
        int sumOfUnmarkedElements = grid.values().stream()
                .filter(value -> !draws.contains(value))
                .mapToInt(Integer::intValue)
                .sum();
        return sumOfUnmarkedElements * lastDraw;
    }

    private List<Integer> getRow(int rowIndex) {
        return IntStream.rangeClosed(0, 4)
                .boxed()
                .map(columnIndex -> new Position(rowIndex, columnIndex))
                .map(grid::get)
                .collect(Collectors.toList());
    }

    private List<Integer> getColumn(int columnIndex) {
        return IntStream.rangeClosed(0, 4)
                .boxed()
                .map(rowIndex -> new Position(rowIndex, columnIndex))
                .map(grid::get)
                .collect(Collectors.toList());
    }
}
