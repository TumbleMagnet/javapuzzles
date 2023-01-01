package be.rouget.puzzles.adventofcode.year2022.day09;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;


public class RopeBridge {

    private static final Logger LOG = LogManager.getLogger(RopeBridge.class);
    private final List<Instruction> instructions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(RopeBridge.class);
        RopeBridge aoc = new RopeBridge(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RopeBridge(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = input.stream()
                .map(Instruction::parse)
                .toList();
    }

    public long computeResultForPart1() {
        return countUniqueTailPositions(2);
    }

    public long computeResultForPart2() {
        return countUniqueTailPositions(10);
    }

    private int countUniqueTailPositions(int sizeOfRope) {
        Rope rope = Rope.buidRope(sizeOfRope);
        Set<Position> tailPositions = Sets.newHashSet();
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.distance(); i++) {
                rope = rope.moveHead(instruction.direction());
                tailPositions.add(rope.tail());
            }
        }
        return tailPositions.size();
    }

}