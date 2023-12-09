package be.rouget.puzzles.adventofcode.year2023.day03;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.SetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;


public class GearRatios {

    private static final Logger LOG = LogManager.getLogger(GearRatios.class);
    private final RectangleMap<EngineCharacter> engineSchematics;
    private final List<PartNumber> parts;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(GearRatios.class);
        GearRatios aoc = new GearRatios(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public GearRatios(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        engineSchematics = new RectangleMap<>(input, EngineCharacter::new);
        parts = extractAllParts();
    }

    private List<PartNumber> extractAllParts() {
        return IntStream.rangeClosed(0, engineSchematics.getHeight()-1)
                .mapToObj(this::extractNumbersOnLine)
                .flatMap(List::stream)
                .filter(this::isAdjacentToASymbol)
                .toList();
    }

    private List<PartNumber> extractNumbersOnLine(int y) {
        List<PartNumber> numbers = Lists.newArrayList();
        PartNumber currentPart = new PartNumber();
        for (int x = 0; x < engineSchematics.getWidth(); x++) {
            Position position = new Position(x, y);
            EngineCharacter currentChar = engineSchematics.getElementAt(position);
            if (currentChar.isDigit()) {
                currentPart.addDigit(currentChar.getMapChar(), position);
            } else {
                // Terminate possible part number
                if (!currentPart.isEmpty()) {
                    numbers.add(currentPart);
                    currentPart = new PartNumber();
                }
            }
        }

        // Terminate possible part number
        if (!currentPart.isEmpty()) {
            numbers.add(currentPart);
        }
        return numbers;
    }

    private boolean isAdjacentToASymbol(PartNumber partNumber) {
        return partNumber.getPositions().stream()
                .map(engineSchematics::enumerateNeighbourPositions)
                .flatMap(List::stream)
                .map(engineSchematics::getElementAt)
                .anyMatch(EngineCharacter::isSymbol);
    }

    public long computeResultForPart1() {
        return parts.stream()
                .mapToInt(PartNumber::getValueAsInt)
                .sum();
    }
    
    public long computeResultForPart2() {
        return engineSchematics.getElements().stream()
                .filter(entry -> entry.getValue().getMapChar().equals("*"))
                .map(Map.Entry::getKey)
                .map(this::getAdjacentParts)
                .filter(list -> list.size() == 2)
                .mapToLong(list -> Long.valueOf(list.get(0).getValueAsInt()) * Long.valueOf(list.get(1).getValueAsInt()))
                .sum();
    }

    private List<PartNumber> getAdjacentParts(Position gearPosition) {
        Set<Position> gearNeighbours = Sets.newHashSet(engineSchematics.enumerateNeighbourPositions(gearPosition));
        return parts.stream()
                .filter(part -> !SetUtils.intersection(gearNeighbours, Sets.newHashSet(part.getPositions())).isEmpty())
                .toList();
    }
}