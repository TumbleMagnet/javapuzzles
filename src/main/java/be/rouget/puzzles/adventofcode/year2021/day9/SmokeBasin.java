package be.rouget.puzzles.adventofcode.year2021.day9;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.MapCharacter;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class SmokeBasin {

    private static final String YEAR = "2021";
    private static final String DAY = "09";

    private static final Logger LOG = LogManager.getLogger(SmokeBasin.class);
    private final RectangleMap<HeightChar> heightMap;
    private final List<Position> lowPoints;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SmokeBasin aoc = new SmokeBasin(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SmokeBasin(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        heightMap = new RectangleMap<>(input, HeightChar::fromInputCharacter);
        LOG.info("Map dimensions are {} x {}", heightMap.getWidth(), heightMap.getHeight());
        lowPoints = findLowPoints();
        LOG.info("Found {} low points.", lowPoints.size());
    }

    public long computeResultForPart1() {
        return lowPoints.stream()
                .mapToInt(position -> heightMap.getElementAt(position).getHeight())
                .map(i -> i + 1)
                .sum();
    }

    public long computeResultForPart2() {
        List<Integer> reversedBasinSizes = lowPoints.stream()
                .map(this::computeSizeOfBasin)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
        return Long.valueOf(reversedBasinSizes.get(0))
                * Long.valueOf(reversedBasinSizes.get(1))
                * Long.valueOf(reversedBasinSizes.get(2));
    }

    private List<Position> findLowPoints() {
        List<Position> lowPoints = Lists.newArrayList();
        for (int x = 0; x < heightMap.getWidth(); x++) {
            for (int y = 0; y < heightMap.getHeight(); y++) {
                Position position = new Position(x, y);
                int currentHeight = heightMap.getElementAt(position).getHeight();
                boolean isLowPoint = heightMap.enumerateNeighbourPositions(position).stream()
                        .filter(n -> n.getX() == position.getX() || n.getY() == position.getY()) // Exclude diagonal neighbours
                        .allMatch(neighbour -> heightMap.getElementAt(neighbour).getHeight() > currentHeight);
                if (isLowPoint) {
                    lowPoints.add(position);
                }
            }
        }
        return lowPoints;
    }

    private int computeSizeOfBasin(Position lowPoint) {
        Set<Position> basin = Sets.newHashSet(lowPoint);
        Queue<Position> toProcess = Queues.newArrayDeque(List.of(lowPoint));
        while (!toProcess.isEmpty()) {
            Position position = toProcess.remove();
            heightMap.enumerateNeighbourPositions(position).stream()
                    .filter(n -> n.getX() == position.getX() || n.getY() == position.getY()) // Exclude diagonals
                    .filter(n -> heightMap.getElementAt(n).getHeight() != 9)
                    .forEach(n -> {
                        if (!basin.contains(n)) {
                            basin.add(n);
                            toProcess.add(n);
                        }
                    });
        }
        return basin.size();
    }

    public static class HeightChar implements MapCharacter {
        private final int height;

        public HeightChar(int height) {
            this.height = height;
        }

        @Override
        public String getMapChar() {
            return String.valueOf(height);
        }

        public static HeightChar fromInputCharacter(String input) {
            return new HeightChar(Integer.parseInt(input));
        }

        public int getHeight() {
            return height;
        }
    }
}