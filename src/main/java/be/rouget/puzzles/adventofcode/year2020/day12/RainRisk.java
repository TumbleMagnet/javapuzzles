package be.rouget.puzzles.adventofcode.year2020.day12;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class RainRisk {

    private static final String YEAR = "2020";
    private static final String DAY = "12";

    private static final Logger LOG = LogManager.getLogger(RainRisk.class);

    private final List<String> input;
    private final List<Instruction> instructions;

    public RainRisk(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.input = input;
        this.instructions = input.stream().map(Instruction::fromInput).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        RainRisk aoc = new RainRisk(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        PositionWithDirection position = new PositionWithDirection(0, 0, 90); // Facing EAST
        for (Instruction instruction : instructions) {
            position = position.applyInstruction(instruction);
        }
        return position.getManhattanDistanceFromPosition(0,0);
    }

    public long computeResultForPart2() {
        PositionWithWaypoint position = new PositionWithWaypoint(0, 0, new Waypoint(10, 1)); // 10 EAST, 1 NORTH
        for (Instruction instruction : instructions) {
            position = position.applyInstruction(instruction);
        }
        return position.getManhattanDistanceFromPosition(0,0);
    }
}