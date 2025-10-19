package be.rouget.puzzles.adventofcode.year2024.day08;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.MapCharacter;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ResonantCollinearity {

    private static final Logger LOG = LogManager.getLogger(ResonantCollinearity.class);
    private final RectangleMap<MapItem> antennaMap;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ResonantCollinearity.class);
        ResonantCollinearity aoc = new ResonantCollinearity(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public ResonantCollinearity(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        antennaMap = new RectangleMap<>(input, MapItem::new);
    }

    public long computeResultForPart1() {

        // Positions of antinodes
        Set<Position> antinodes = Sets.newHashSet();

        // Extract the list of positions of each type of antenna
        Map<MapItem, List<Position>> antennaPositions = antennaMap.getElements().stream()
                .filter(element -> element.getValue().isAnAntenna())
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        // For each type of antenna, for each pair of antenna, generate the antinode
        for (Map.Entry<MapItem, List<Position>> entry : antennaPositions.entrySet()) {
            List<Position> positions = entry.getValue();
            LOG.info("Found {} antennas of type {}", positions.size(), entry.getKey().value());
            if (positions.size() > 1) {
                for (int i = 0; i < positions.size() - 1; i++) {
                    for (int j = i + 1; j < positions.size(); j++) {
                        Position first = positions.get(i);
                        Position second = positions.get(j);
                        Position antinode1 = symmetricOf(first, second);
                        if (antennaMap.isPositionInMap(antinode1)) {
                            antinodes.add(antinode1);
                        }
                        Position antinode2 = symmetricOf(second, first);
                        if (antennaMap.isPositionInMap(antinode2)) {
                            antinodes.add(antinode2);
                        }
                    }
                }
            }
        }

        return antinodes.size();
    }

    private Position symmetricOf(Position first, Position second) {
        int deltaX = first.getX() - second.getX();
        int deltaY = first.getY() - second.getY();
        return new Position(first.getX() + deltaX, first.getY() + deltaY);
    }

    public long computeResultForPart2() {
        return -1;
    }

    public record MapItem(String value) implements MapCharacter {
        @Override
        public String getMapChar() {
            return value;
        }

        public boolean isEmpty() {
            return ".".equals(value);
        }

        public boolean isAnAntenna() {
            return !isEmpty();
        }
    }
}