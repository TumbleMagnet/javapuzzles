package be.rouget.puzzles.adventofcode.year2022.day17;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;
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
        RockCycle rockCycle = detectCycle();
        long numberOfRocks = 1000000000000L;
        long numberOfCycles = numberOfRocks / rockCycle.length();
        long remainder = numberOfRocks % rockCycle.length();
        int remainderHeight = computeHeight(Math.toIntExact(remainder));
        return numberOfCycles * rockCycle.height() + remainderHeight;
    }

    private RockCycle detectCycle() {
        
        // Record states for a large number of rocks and use the data to find the cycle
        List<RockState> states = Lists.newArrayList();
        computeHeight(5000, states);
        
        // Floyd's cycle-finding algorithm: tortoise and hare

        // Main phase of algorithm: finding a repetition x_i = x_2i.
        // The hare moves twice as quickly as the tortoise and
        // the distance between them increases by 1 at each step.
        // Eventually they will both be inside the cycle and then,
        // at some point, the distance between them will be
        // divisible by the period λ.
        int tortoise = 1;
        int hare = 2;
        while (!states.get(tortoise).equals(states.get(hare))) {
            tortoise = tortoise + 1;
            hare = hare + 2;
        }
        LOG.info("First meeting: turtoise: {} - hare: {}", tortoise, hare);

        // At this point the tortoise position, ν, which is also equal
        // to the distance between hare and tortoise, is divisible by
        // the period λ. So hare moving in cycle one step at a time, 
        // and tortoise (reset to x0) moving towards the cycle, will 
        // intersect at the beginning of the cycle. Because the 
        // distance between them is constant at 2ν, a multiple of λ,
        // they will agree as soon as the tortoise reaches index μ.
        
        // Find the position μ of first repetition.
        int mu = 0;
        tortoise = 0;
        while (!states.get(tortoise).equals(states.get(hare))) {
            tortoise = tortoise + 1;
            hare = hare + 1; // # Hare and tortoise move at same speed
            mu++;
        }
        LOG.info("mu: {}", mu);
        
        // Find the length of the shortest cycle starting from x_μ
        // The hare moves one step at a time while tortoise is still.
        // lam is incremented until λ is found.
        int lambda = 1;
        hare = tortoise + 1;
        while (!states.get(tortoise).equals(states.get(hare))) {
            hare +=1;
            lambda +=1;
        }
        LOG.info("Cycle length: {}", lambda);
        
        // Now that the length of the cycle is known, compute its height
        int cycleHeight = computeHeight(mu+lambda) - computeHeight(mu);
        LOG.info("Cycle length: {} - height: {}", lambda, cycleHeight);
        
        return new RockCycle(lambda, cycleHeight);
    }

    private int computeHeight(int numberOfRocks) {
        return computeHeight(numberOfRocks, null);
    }

    private int computeHeight(int numberOfRocks, List<RockState> states) {
        Chamber chamber = new Chamber(7);
        Shape currentShape = null;
        int jetIndex = 0;
        for (int rockIndex = 0; rockIndex < numberOfRocks; rockIndex++) {
            currentShape = Shape.getNextShape(currentShape);
            Position bottomLeftPositionOfNextRock = chamber.bottomLeftPositionOfNextRock();
            Position topLeftPositionOfNextRock = new Position(bottomLeftPositionOfNextRock.getX(), bottomLeftPositionOfNextRock.getY()-currentShape.getHeight()+1);
            Rock rock = new Rock(topLeftPositionOfNextRock, currentShape);
            int jetIndexAtSpawn = jetIndex;
            while (true) {
                Rock movedByJet = rock.move(getJetDirection(jetIndex));
                jetIndex = (jetIndex + 1) % jetPatterns.size();
                if (chamber.doesRockFit(movedByJet)) {
                    rock = movedByJet;
                }
                Rock movedDown = rock.move(DOWN);
                if (chamber.doesRockFit(movedDown)) {
                    rock = movedDown;
                } else {
                    if (states != null) {
                        // Record state for that rock
                        Position move = new Position(rock.position().getX() - topLeftPositionOfNextRock.getX(), rock.position().getY() - topLeftPositionOfNextRock.getY());
                        RockState rockState = new RockState(currentShape, jetIndexAtSpawn, move);
                        states.add(rockState);
                    }
                    break;
                }
            }
            chamber.addRock(rock);
        }
        return chamber.getRockHeight();
    }

    private Direction getJetDirection(int jetIndex) {
        return jetPatterns.get(jetIndex);
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