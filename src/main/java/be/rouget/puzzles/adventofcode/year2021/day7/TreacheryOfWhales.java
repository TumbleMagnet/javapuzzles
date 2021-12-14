package be.rouget.puzzles.adventofcode.year2021.day7;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongBiFunction;
import java.util.stream.Collectors;

public class TreacheryOfWhales {

    private static final String YEAR = "2021";
    private static final String DAY = "07";

    private static final Logger LOG = LogManager.getLogger(TreacheryOfWhales.class);
    private final List<Integer> positions;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TreacheryOfWhales aoc = new TreacheryOfWhales(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TreacheryOfWhales(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        positions = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        LOG.info("There are {} crabs...", positions.size());
    }

    public long computeResultForPart1() {
        return solve((a, b) -> costToMoveForPart1(a, b));
    }

    public long computeResultForPart2() {
        return solve((a, b) -> costToMoveForPart2(a, b));
    }

    private Long solve(ToLongBiFunction<Integer, Integer> costFunction) {
        List<Integer> sortedPositions = positions.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        Integer minPosition = sortedPositions.get(0);
        Integer maxPosition = sortedPositions.get(sortedPositions.size() - 1);

        Integer bestPosition = null;
        Long bestCost = null;
        for (int tentativePosition = minPosition; tentativePosition <= maxPosition; tentativePosition++) {

            final int targetPosition = tentativePosition;
            long cost = sortedPositions.stream()
                    .mapToLong(crabPosition -> costFunction.applyAsLong(crabPosition, targetPosition))
                    .sum();
            if (bestPosition == null || cost < bestCost) {
                bestPosition = tentativePosition;
                bestCost = cost;
            }
        }
        return bestCost;
    }

    private long costToMoveForPart1(int startPosition, int targetPosition) {
        return Math.abs(startPosition - targetPosition);
    }

    public long costToMoveForPart2(int startPosition, int targetPosition) {
        int distance = Math.abs(startPosition - targetPosition);
        return distance * (distance + 1) / 2;
    }


}