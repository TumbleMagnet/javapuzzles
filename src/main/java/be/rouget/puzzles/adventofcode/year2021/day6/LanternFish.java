package be.rouget.puzzles.adventofcode.year2021.day6;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LanternFish {

    private static final String YEAR = "2021";
    private static final String DAY = "06";

    private static final Logger LOG = LogManager.getLogger(LanternFish.class);
    private static final Map<InputRecord, Long> knownDescendants = Maps.newHashMap();
    private final List<Integer> startFishes;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        LanternFish aoc = new LanternFish(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public LanternFish(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        startFishes = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        LOG.info("At the start, there are {} fishes ", startFishes.size());
    }

    public long computeResultForPart1() {
        return startFishes.stream()
                .mapToLong(f -> countDescendants(f, 80))
                .sum();
    }

    public long computeResultForPart2() {
        return startFishes.stream()
                .mapToLong(f -> countDescendants(f, 256))
                .sum();
    }

    /*
        Number of fishes from 1 fish with ageFishAge and X day
        1 fish
        + child 1 and its children (recursive call age 8 and remaining days (X-fishAge-1)
        + child 2 and its children (recursive call age 8 and remaining days (X-fishAge-1) - 6)
        + child 3 and its children (recursive call age 8 and remaining days (X-fishAge-1) - 12)
     */
    public static Long countDescendants(Integer fishAge, int numberOfDays) {

        InputRecord input = new InputRecord(fishAge, numberOfDays);
        Long knownResult = knownDescendants.get(input);
        if (knownResult != null) {
            return knownResult;
        }

        long total = 1; // count starting fish
        if (numberOfDays < (fishAge + 1)) {
            // Not enough time to create descendants
            knownDescendants.put(input, total);
            return total;
        }
        int remainingDays = numberOfDays - fishAge -1;
        while (remainingDays >= 0) {
            total += countDescendants(8, remainingDays);
            remainingDays -= 7;
        }

        knownDescendants.put(input, total);
        return total;
    }

    @Value
    public static class InputRecord {
        Integer fishAge;
        int numberOfDays;
    }

}