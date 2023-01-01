package be.rouget.puzzles.adventofcode.year2022.day05;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.stream.MyCollectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class SupplyStacks {

    private static final Logger LOG = LogManager.getLogger(SupplyStacks.class);
    private final List<Deque<String>> inputStacks;
    private final List<MoveInstruction> moveInstructions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(SupplyStacks.class);
        SupplyStacks aoc = new SupplyStacks(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public SupplyStacks(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        List<String> inputForStacks = input.stream()
                .takeWhile(StringUtils::isNotBlank)
                .toList();
        inputStacks = parseStacks(inputForStacks);
        LOG.info("Found {} stacks...", inputStacks.size());

        moveInstructions = input.stream()
                .dropWhile(StringUtils::isNotBlank)
                .filter(StringUtils::isNotBlank)
                .map(MoveInstruction::parse)
                .toList();
        LOG.info("Found {} instruction...", moveInstructions.size());
    }

    protected static List<Deque<String>> parseStacks(List<String> inputForStacks) {
        String lineWithStackNumbers = inputForStacks.get(inputForStacks.size() - 1);
        int numberOfStacks = (lineWithStackNumbers.length() + 1) / 4;

        List<String> stacksFromBottomToTop = inputForStacks.stream()
                .filter(line -> !line.startsWith(" 1"))
                .collect(MyCollectors.toListReversed());

        List<Deque<String>> stacks = IntStream.range(0, numberOfStacks)
                .mapToObj(i -> new ArrayDeque<String>())
                .collect(Collectors.toList()); // NOSONAR (using toList() requires casting ArrayDeque into Deque

        for (String line : stacksFromBottomToTop) {
            List<String> contents = extractContentOfCrate(line);
            for (int i = 0; i <numberOfStacks; i++) {
                String crateContent = contents.get(i);
                if (StringUtils.isNotBlank(crateContent)) {
                    stacks.get(i).addLast(crateContent);
                }
            }
        }
        return stacks;
    }

    protected static List<String> extractContentOfCrate(String lineOfCrates) {
        return IntStream.iterate(1, i -> i < lineOfCrates.length() - 1, i -> i + 4)
                .mapToObj(i -> lineOfCrates.substring(i, i + 1))
                .toList();
    }

    public String computeResultForPart1() {
        List<Deque<String>> stacks = cloneStacks(inputStacks);
        moveInstructions.forEach(i -> executeInstructionPart1(stacks, i));
        return extractTopContents(stacks);
    }

    private static String extractTopContents(List<Deque<String>> stacks) {
        return stacks.stream()
                .map(Deque::getLast)
                .collect(Collectors.joining());
    }

    private void executeInstructionPart1(List<Deque<String>> stacks, MoveInstruction instruction) {
        for (int i = 0; i < instruction.quantity(); i++) {
            String element = stacks.get(instruction.from() -1).removeLast();
            stacks.get(instruction.to() -1).addLast(element);
        }
    }

    public String computeResultForPart2() {
        List<Deque<String>> stacks = cloneStacks(inputStacks);
        moveInstructions.forEach(i -> executeInstructionPart2(stacks, i));
        return extractTopContents(stacks);
    }

    private void executeInstructionPart2(List<Deque<String>> stacks, MoveInstruction instruction) {

        Deque<String> fromStack = stacks.get(instruction.from()-1);
        Deque<String> toStack = stacks.get(instruction.to()-1);
        Deque<String> tempStack = new ArrayDeque<>();

        for (int i = 0; i < instruction.quantity(); i++) {
            tempStack.addLast(fromStack.removeLast());
        }
        for (int i = 0; i < instruction.quantity(); i++) {
            toStack.addLast(tempStack.removeLast());
        }
    }


    private List<Deque<String>> cloneStacks(List<Deque<String>> inputStacks) {
        return inputStacks.stream()
                .map(stack -> (Deque<String>) new ArrayDeque<>(stack))
                .toList();
    }
}