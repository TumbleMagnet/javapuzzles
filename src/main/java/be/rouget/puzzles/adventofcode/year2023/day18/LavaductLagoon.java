package be.rouget.puzzles.adventofcode.year2023.day18;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;


public class LavaductLagoon {

    private static final Logger LOG = LogManager.getLogger(LavaductLagoon.class);
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(LavaductLagoon.class);
        LavaductLagoon aoc = new LavaductLagoon(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public LavaductLagoon(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return computeAreaOfLagoon(DigInstruction::parseForPart1);
    }

    public long computeResultForPart2() {
        return computeAreaOfLagoon(DigInstruction::parseForPart2);
    }

    private long computeAreaOfLagoon(Function<String, DigInstruction> parsingFunction) {
        List<DigInstruction> instructions = input.stream()
                .map(parsingFunction)
                .toList();

        // Total area of the lagoon is given by:
        // - A1: the internal of the area of the polygon whose vertices are the center of the "tiles" of the trench
        // - A2 the area of the trench outside the inside polygon considered for A1
        //
        // A1 is computed using the shoelace formula: https://www.themathdoctors.org/polygon-coordinates-and-areas/
        // A1 = 0.5 * ABS( (x1y2 - x2y1) + (x2y3 - x3y2) + ... + (xNy1 - x1yN) )
        //
        // A2 corresponds to:
        // - the sum of all the edges * 0.5 (area of the rectangle with the width of half a tile)
        // - the 4 left-over corners of a quarter of title each
        // A2 = 0.5 * perimeter of the inside polygon + 1
        
        long shoelaceDeterminant = 0;
        long perimeter = 0;
        Position current = new Position(0L, 0L);
        for (DigInstruction instruction : instructions) {
            Position p1 = current;
            Position p2 = dig(p1, instruction);
            // Shoelace formula: (x1y2 - x2y1)
            shoelaceDeterminant += (p1.x() * p2.y()) - (p2.x() * p1.y());
            perimeter += instruction.distance();
            current = p2;
        }
        long a1 = Math.abs(shoelaceDeterminant) / 2;
        long a2 = (perimeter / 2) + 1;
        return a1 + a2;
    }

    private Position dig(Position start, DigInstruction instruction) {
        long length = instruction.distance();
        return switch(instruction.direction()) {
            case RIGHT -> new Position(start.x() + length,  start.y());
            case LEFT -> new Position(start.x() - length,  start.y());
            case UP -> new Position(start.x(),  start.y() - length);
            case DOWN -> new Position(start.x() ,  start.y() + length);
        };
    }
}