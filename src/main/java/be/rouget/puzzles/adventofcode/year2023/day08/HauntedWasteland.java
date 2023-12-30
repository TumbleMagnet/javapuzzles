package be.rouget.puzzles.adventofcode.year2023.day08;

import be.rouget.puzzles.adventofcode.util.AocMathUtils;
import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class HauntedWasteland {

    private static final Logger LOG = LogManager.getLogger(HauntedWasteland.class);
    
    private final List<Instruction> instructions;
    private final Map<String, Node> nodes;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(HauntedWasteland.class);
        HauntedWasteland aoc = new HauntedWasteland(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public HauntedWasteland(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        
        // Parse instructions
        String instructionsInput = input.getFirst();
        instructions = AocStringUtils.extractCharacterList(instructionsInput).stream()
                .map(Instruction::valueOf)
                .toList();
        LOG.info("Found {} instructions", instructions.size());

        // Parse nodes
        nodes = input.subList(2, input.size()).stream()
                .map(Node::parse)
                .collect(Collectors.toMap(Node::name, Function.identity()));
        LOG.info("Found {} nodes", nodes.size());
    }

    public long computeResultForPart1() {
        // Start with AAA, follow instructions and count number of steps until ZZZ
        return countStepsToFirstDestination("AAA", "ZZZ"::equals);
    }

    public long computeResultForPart2() {

        // Find all starting nodes (name ends with "A")
        List<String> startingNodes = nodes.keySet().stream()
                .filter(name -> name.endsWith("A"))
                .toList();
        LOG.info("Found {} starting nodes...", startingNodes.size());

        // Count number of steps to find the first destination for each starting node (we assume they cycle after that)
        List<Long> steps = startingNodes.stream()
                .map(current -> this.countStepsToFirstDestination(current, name -> name.endsWith("Z")))
                .map(Long::valueOf)
                .toList();

        // Assuming that the cycles are "aligned", the min number of steps is the LCM of the cycle lengths
        return AocMathUtils.lcmOfList(steps);
    }
    
    private String doStep(String current, Instruction instruction) {
        Node node = nodes.get(current);
        if (node == null) {
            throw new IllegalStateException("Cannot find node with name: " + current);
        }
        current = instruction == Instruction.L ? node.left() : node.right();
        return current;
    }

    private Instruction getInstructionForStep(int step) {
        int index = step % instructions.size();
        return instructions.get(index);
    }

    private int countStepsToFirstDestination(String start, Predicate<String> isDestination) {
        String current = start;
        int step = 0;
        while (!isDestination.test(current)) {
            Instruction instruction = getInstructionForStep(step);
            current = doStep(current, instruction);
            step++;
        }
        return step;
    }
}