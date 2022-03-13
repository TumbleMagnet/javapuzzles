package be.rouget.puzzles.adventofcode.year2016.day19;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;

public class AnElephantNamedJoseph {

    private static final Logger LOG = LogManager.getLogger(AnElephantNamedJoseph.class);
    public static final int STARTING_COUNT = 3018458;

    public static void main(String[] args) {
        AnElephantNamedJoseph aoc = new AnElephantNamedJoseph();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return solvePart1Recursively(STARTING_COUNT);
    }

    public static int solvePart1Recursively(int count) {
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
            return 2 * solvePart1Recursively(count/2) -1;
        } else {
            return 2 * solvePart1Recursively((count-1)/2) +1;
        }
    }

    public static int solvePart1WithSimulation(int count) {

        // Build a linked list with the elves
        LinkedList<Integer> elves = new LinkedList<>();
        for(int i = 1; i<=count; i++) {
            elves.addLast(i);
        }

        // Run simulation
        boolean take = false;
        while (elves.size() != 1) {
            Iterator<Integer> itr = elves.iterator();
            while (itr.hasNext()) {
                itr.next();
                if (take) {
                    itr.remove();
                }
                take = !take;
            }
        }
        return elves.pollFirst();
    }

    public long computeResultForPart2() {
        return solvePart2Analytically(STARTING_COUNT);
    }

    public static int solvePart2Analytically(int x) {
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

    public static int solvePart2WithSimulation(int size) {

        // Split elves in two lists of same length
        LinkedList<Integer> que1 = new LinkedList<>();
        LinkedList<Integer> que2 = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            if (i <= size / 2) {
                que1.addLast(i);
            }
            else {
                que2.addLast(i);
            }
        }

        // Run simulation
        while(que1.size() + que2.size() != 1) {

            // x is current elf
            int x = que1.pollFirst();

            // Remove target elf
            if (que1.size() == que2.size()) {
                que1.pollLast();
            } else {
                que2.pollFirst();
            }

            // Add current elf to the end
            que2.addLast(x);

            // Re-balance lists (also make sure that last element is in queue 1)
            int a = que2.pollFirst();
            que1.addLast(a);
        }
        return que1.pollFirst();
    }
}