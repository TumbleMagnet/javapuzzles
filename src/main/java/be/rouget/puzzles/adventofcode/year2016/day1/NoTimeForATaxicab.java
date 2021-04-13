package be.rouget.puzzles.adventofcode.year2016.day1;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NoTimeForATaxicab {

    private static final String YEAR = "2016";
    private static final String DAY = "01";

    private static final Logger LOG = LogManager.getLogger(NoTimeForATaxicab.class);
    private final List<Instruction> instructions;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        NoTimeForATaxicab aoc = new NoTimeForATaxicab(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public NoTimeForATaxicab(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        String inputLine = input.get(0);
        instructions = Arrays.stream(inputLine.split(", ")).map(Instruction::fromInput).collect(Collectors.toList());
        LOG.info("Input has {} instructions...", instructions.size());
    }

    public long computeResultForPart1() {
        BlockPosition startingPosition = new BlockPosition(new Position(0, 0), Direction.UP);
        BlockPosition currentPosition = startingPosition;
        for (Instruction instruction : instructions) {
            currentPosition = currentPosition.followInstruction(instruction);
        }
        return currentPosition.distanceFrom(startingPosition);
    }

    public long computeResultForPart2() {

        BlockPosition startingPosition = new BlockPosition(new Position(0, 0), Direction.UP);
        Set<Position> visitedPositions = Sets.newHashSet(startingPosition.getPosition());
        BlockPosition currentPosition = startingPosition;
        for (Instruction instruction : instructions) {
            List<BlockPosition> newBlockPositions = currentPosition.followInstructionStepByStep(instruction);
            List<Position> newPositions = newBlockPositions.stream().map(BlockPosition::getPosition).collect(Collectors.toList());
            for (Position newPosition : newPositions) {
                if (visitedPositions.contains(newPosition)) {
                    return BlockPosition.computeDistance(newPosition, startingPosition.getPosition());
                }
                visitedPositions.add(newPosition);
            }
            currentPosition = newBlockPositions.get(newBlockPositions.size()-1);
        }
        throw new IllegalStateException("Did not find a position which was visited twice!");
    }
}