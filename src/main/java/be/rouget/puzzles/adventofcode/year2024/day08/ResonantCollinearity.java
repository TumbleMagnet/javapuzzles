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
import java.util.function.BiFunction;
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
        return solve(this::computeAntinode);
    }

    private long solve(BiFunction<Position, Position, Set<Position>> antinodeFunction) {
        // Positions of antinodes
        Set<Position> antinodes = Sets.newHashSet();

        // Extract the list of positions of each type of antenna
        Map<MapItem, List<Position>> antennaPositions = antennaMap.getElements().stream()
                .filter(element -> element.getValue().isAnAntenna())
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        // For each type of antenna, for each pair of antenna, generate the antinode
        for (Map.Entry<MapItem, List<Position>> entry : antennaPositions.entrySet()) {
            List<Position> positions = entry.getValue();
            if (positions.size() > 1) {
                for (int i = 0; i < positions.size() - 1; i++) {
                    for (int j = i + 1; j < positions.size(); j++) {
                        Position first = positions.get(i);
                        Position second = positions.get(j);
                        antinodes.addAll(antinodeFunction.apply(first, second));
                        antinodes.addAll(antinodeFunction.apply(second, first));
                    }
                }
            }
        }

        return antinodes.size();
    }

    // For part 1, we consider only the antinode which is a mirror of second from first
    private Set<Position> computeAntinode(Position first, Position second) {
        Set<Position> antinodes = Sets.newHashSet();
        int deltaX = first.getX() - second.getX();
        int deltaY = first.getY() - second.getY();
        Position antinode = new Position(first.getX() + deltaX, first.getY() + deltaY);
        if (antennaMap.isPositionInMap(antinode)) {
            antinodes.add(antinode);
        }
        return antinodes;
    }

    public long computeResultForPart2() {
        return solve(this::computeAntiNodesWithHarmonics);
    }

    // For part 2, we consider alls the antinodes aligned with second, starting from (and including) first
    private Set<Position> computeAntiNodesWithHarmonics(Position source, Position other) {
        Set<Position> antinodes = Sets.newHashSet();

        int deltaX = source.getX() - other.getX();
        int deltaY = source.getY() - other.getY();

        Position current = source;
        while (antennaMap.isPositionInMap(current)) {
            antinodes.add(current);
            current = new Position(current.getX() + deltaX, current.getY() + deltaY);
        }
        return antinodes;
    }

    public record MapItem(String value) implements MapCharacter {
        @Override
        public String getMapChar() {
            return value;
        }

        public boolean isAnAntenna() {
            return !".".equals(value);
        }
    }
}