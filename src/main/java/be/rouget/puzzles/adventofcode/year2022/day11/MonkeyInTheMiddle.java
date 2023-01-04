package be.rouget.puzzles.adventofcode.year2022.day11;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class MonkeyInTheMiddle {

    private static final Logger LOG = LogManager.getLogger(MonkeyInTheMiddle.class);
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(MonkeyInTheMiddle.class);
        MonkeyInTheMiddle aoc = new MonkeyInTheMiddle(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public MonkeyInTheMiddle(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        this.input = input;
    }

    public long computeResultForPart1() {
        return computeMonkeyBusiness(20, true);
    }

    public long computeResultForPart2() {
        return computeMonkeyBusiness(10000, false);
    }
    
    private long computeMonkeyBusiness(int numberOfRounds, boolean inspectionDividesWorryLevel) {
        
        // Parse monkeys from input
        List<Monkey> monkeys = parseMonkeys(input);
        
        monkeys.forEach(monkey -> monkey.setInspectionDividesWorryLevel(inspectionDividesWorryLevel));
        
        // Compute common modulo
        Long commonModulo = monkeys.stream()
                .map(Monkey::getTestDivider)
                .reduce(1L, (l1, l2) -> l1 * l2);
        monkeys.forEach(m -> m.setCommonModulo(commonModulo));

        // Execute rounds
        for (int i = 0; i < numberOfRounds; i++) {
            LOG.info("Round {}", i+ 1);
            executeRound(monkeys);
        }
        
        // Compute resulting monkey business
        List<Long> scores = monkeys.stream()
                .map(Monkey::getNumberOfItems)
                .sorted(Comparator.reverseOrder())
                .toList();
        return scores.get(0) * scores.get(1);
    }

    private static List<Monkey> parseMonkeys(List<String> input) {
        List<String> inputWithoutBlankLines = input.stream()
                .filter(StringUtils::isNotBlank)
                .toList();
        List<List<String>> inputPerMonkey = ListUtils.partition(inputWithoutBlankLines, 6);
        return inputPerMonkey.stream()
                .map(Monkey::parse)
                .toList();
    }

    private void executeRound(List<Monkey> monkeys) {
        for (Monkey monkey : monkeys) {
            Optional<ThrownItem> optionalThrownItem = monkey.throwNextItem();
            while (optionalThrownItem.isPresent()) {
                ThrownItem thrownItem = optionalThrownItem.get();
                monkeys.get(thrownItem.destinationIndex()).receiveItem(thrownItem.worryLevel());
                optionalThrownItem = monkey.throwNextItem();
            }
        }
    }

}