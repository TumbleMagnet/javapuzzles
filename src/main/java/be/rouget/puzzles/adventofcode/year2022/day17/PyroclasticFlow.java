package be.rouget.puzzles.adventofcode.year2022.day17;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static be.rouget.puzzles.adventofcode.util.map.Direction.DOWN;


public class PyroclasticFlow {

    private static final Logger LOG = LogManager.getLogger(PyroclasticFlow.class);
    private final List<Direction> jetPatterns;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PyroclasticFlow.class);
        PyroclasticFlow aoc = new PyroclasticFlow(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PyroclasticFlow(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        jetPatterns = parseJetPatterns(input.get(0));
    }

    public long computeResultForPart1() {
        return computeHeight(2022);
    }

    public long computeResultForPart2() {
        // Inspecting the function "numberOfRocks => height" every time the jetIndex cycles shows that it is cyclic:
        // 1695 rocks always produce a height of 2610
        long cycleLength =  1695L;
        long cycleHeight = 2610L;

        long numberOfRocks = 1000000000000L;
        long numberOfCycles = numberOfRocks / cycleLength;
        long remainder = numberOfRocks % cycleLength;
        int remainderHeight = computeHeight(Math.toIntExact(remainder));

        return numberOfCycles * cycleHeight + remainderHeight;
    }

    private int computeHeight(int numberOfRocks) {
        Chamber chamber = new Chamber(7);
        Shape currentShape = null;
        int jetIndex = 0;
        for (int rockIndex = 0; rockIndex < numberOfRocks; rockIndex++) {
            currentShape = Shape.getNextShape(currentShape);
            Position bottomLeftPositionOfNextRock = chamber.bottomLeftPositionOfNextRock();
            Position topLeftPositionOfNextRock = new Position(bottomLeftPositionOfNextRock.getX(), bottomLeftPositionOfNextRock.getY()-currentShape.getHeight()+1);
            Rock rock = new Rock(topLeftPositionOfNextRock, currentShape);
            while (true) {
                Rock movedByJet = rock.move(getJetDirection(jetIndex++));
                if (chamber.doesRockFit(movedByJet)) {
                    rock = movedByJet;
                }
                Rock movedDown = rock.move(DOWN);
                if (chamber.doesRockFit(movedDown)) {
                    rock = movedDown;
                } else {
                    break;
                }
            }
            chamber.addRock(rock);
        }
        return chamber.getRockHeight();
    }

    private Direction getJetDirection(int jetIndex) {
        return jetPatterns.get(jetIndex % jetPatterns.size());
    }

    private static List<Direction> parseJetPatterns(String input) {
        return AocStringUtils.extractCharacterList(input).stream()
                .map(PyroclasticFlow::parseDirection)
                .toList();
    }

    private static Direction parseDirection(String input) {
        return switch (input) {
            case "<" -> Direction.LEFT;
            case ">" -> Direction.RIGHT;
            default -> throw new IllegalArgumentException("Illegal value for jet pattern: " + input);
        };
    }
}