package be.rouget.puzzles.adventofcode.year2016.day19;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnElephantNamedJoseph {

    private static final Logger LOG = LogManager.getLogger(AnElephantNamedJoseph.class);
    private static final int STARTING_COUNT = 3018458;

    public static void main(String[] args) {
        AnElephantNamedJoseph aoc = new AnElephantNamedJoseph();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return solvePart1(STARTING_COUNT);
    }

    public static int solvePart1(int count) {
        if (count == 1) {
            return 1;
        }
        // Solve recursively: after going through the circle once, we get a smaller circle with half elements
        // and which are either:
        // - [1, 3, 5, .., n-1] if is even
        // - [3, 5, .., n] if n is odd
        // So if we know how to solve the smaller list for 1, 2, .., n we just need to translate that answer
        // to the original indexes in the initial circle.
        if (count % 2 == 0) {
            return 2 * solvePart1(count/2) -1;
        } else {
            return 2 * solvePart1((count-1)/2) +1;
        }
    }

    public long computeResultForPart2() {
        return solvePart2(STARTING_COUNT);
    }

    public static int solvePart2(int x) {
        // Derived from looking at output from solving manually for small values.
        // Find n such that n <= x <= 3n-3 then:
        // - for the first half of the interval: values are 1, 2, ..., n-1
        // - for the second half of the interval, values increase by 2: n+1, n+3, ..., 3n-3
        int n = 2;
        while (!(n <= x && x <= (3*n-3))) {
            n = 3*n-2;
        }
        LOG.info("Input: {} -> n={}", x, n);
        if (x <= (2 * n - 2)) {
            return 1 + x - n;
        } else {
            return 2*x-3*n+3;
        }
    }


    public static int solvePart2Manually(int count) {
        return solvePart2Manually(IntStream.rangeClosed(1, count).boxed().collect(Collectors.toList()));
    }

    public static int solvePart2Manually(List<Integer> input) {
        if (input.size() <= 2) {
            return input.get(0);
        }
        int indexToRemove = input.size() / 2;
        List<Integer> result = Lists.newArrayList();

        // Skip first, copy elements with index to remove
        for (int i = 1; i < input.size(); i++) {
            if (i != indexToRemove) {
                result.add(input.get(i));
            }
        }
        // Add first at the end
        result.add(input.get(0));

        // Solve resulting list
        return solvePart2Manually(result);
    }
}