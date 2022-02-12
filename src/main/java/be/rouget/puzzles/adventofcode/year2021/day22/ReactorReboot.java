package be.rouget.puzzles.adventofcode.year2021.day22;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ReactorReboot {

    private static final Logger LOG = LogManager.getLogger(ReactorReboot.class);
    private static final int CUBE_MIN = -50;
    private static final int CUBE_MAX = 50;

    private final List<RebootInstruction> instructions;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(ReactorReboot.class);
        ReactorReboot aoc = new ReactorReboot(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public ReactorReboot(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = input.stream()
                .map(RebootInstruction::parse)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        // Compute the state of each cube in the initialization zone by applying all rules which impact it
        long onCubes = 0;
        for (int x = CUBE_MIN; x < CUBE_MAX + 1; x++) {
            for (int y = CUBE_MIN; y < CUBE_MAX + 1; y++) {
                for (int z = CUBE_MIN; z < CUBE_MAX + 1; z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    CubeState finalState = computeFinalState(coordinates);
                    if (CubeState.ON == finalState) {
                        onCubes++;
                    }
                }
            }
        }
        return onCubes;
    }

    private CubeState computeFinalState(Coordinates coordinates) {
        CubeState state = CubeState.OFF;
        for (RebootInstruction instruction : instructions) {
            if (instruction.contains(coordinates)) {
                state = instruction.getTargetState();
            }
        }
        return state;
    }

    public long computeResultForPart2() {
        List<Cuboid> onCubes = Lists.newArrayList();
        for (RebootInstruction instruction : instructions) {
            onCubes = removeIntersection(onCubes, instruction.getTargetCuboid());
            if (instruction.getTargetState() == CubeState.ON) {
               onCubes.add(instruction.getTargetCuboid());
            }
        }
        return onCubes.stream()
                .mapToLong(Cuboid::getVolume)
                .sum();
    }

    private List<Cuboid> removeIntersection(List<Cuboid> onCubes, Cuboid cubeToRemove) {
        return onCubes.stream()
                .map(c -> c.minus(cubeToRemove))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}