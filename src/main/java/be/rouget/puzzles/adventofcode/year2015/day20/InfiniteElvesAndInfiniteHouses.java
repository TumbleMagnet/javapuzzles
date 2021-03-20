package be.rouget.puzzles.adventofcode.year2015.day20;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.math3.primes.Primes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InfiniteElvesAndInfiniteHouses {

    private static final String INPUT = "36000000";
    private static final Logger LOG = LogManager.getLogger(InfiniteElvesAndInfiniteHouses.class);

    public static void main(String[] args) {

        // It is slower to use divisors to compute the number of gifts for each house
        // rather than just iterating of elves and their visits once you known that the maximum number of elves
        // (and houses) which need to be considered is target / 10.
        // - Iterating ranges from 80 to 500 ms (for 360,000 houses)
        // - Using divisors is about 10 seconds (for about 850,000 houses).

        InfiniteElvesAndInfiniteHouses aoc = new InfiniteElvesAndInfiniteHouses(INPUT);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
        LOG.info("Result for part 1 (with divisors) is: " + aoc.computeResultForPart1WithDivisors());
        LOG.info("Result for part 2 (with divisors) is: " + aoc.computeResultForPart2WithDivisors());
    }

    private final String input;

    public InfiniteElvesAndInfiniteHouses(String input) {
        this.input = input;
        LOG.info("Input is {}", this.input);
    }

    public long computeResultForPart1() {

        final int minimumNumberOfGifts = Integer.parseInt(input);
        final int giftsPerVisit = 10;

        // Compute number of gits for all houses with number <= number of gits / 10
        // (since every house has at least house number * 10 gifts)
        int numberOfHouses = minimumNumberOfGifts / giftsPerVisit;
        int[] houses = new int[numberOfHouses + 1]; // Array has one more element for house index 0 (not used)
        for (int elfIndex = 1; elfIndex <= numberOfHouses; elfIndex++) {
            for (int houseIndex = elfIndex; houseIndex <= numberOfHouses; houseIndex += elfIndex) {
                houses[houseIndex] += elfIndex * giftsPerVisit;
            }
        }

        // Find the number of the first house with enough gifts
        for (int i = 1; i <= numberOfHouses; i++) {
            if (houses[i] >= minimumNumberOfGifts) {
                return i;
            }
        }
        throw new IllegalStateException("Did not find a solution");
    }

    public long computeResultForPart2() {

        final int minimumNumberOfGifts = Integer.parseInt(input);
        final int giftsPerVisit = 11;
        final int maxNumberOfVisitPerElf = 50;

        // Compute number of gits for all houses with number <= number of gits / 11
        // (since every house has at least house number * 11 gifts)
        int numberOfHouses = minimumNumberOfGifts / giftsPerVisit;
        int[] houses = new int[numberOfHouses + 1]; // Array has one more element for house index 0 (not used)
        for (int elfIndex = 1; elfIndex <= numberOfHouses; elfIndex++) {
            int visitCount = 0;
            for (int houseIndex = elfIndex; houseIndex <= numberOfHouses && visitCount < maxNumberOfVisitPerElf; houseIndex += elfIndex) {
                houses[houseIndex] += elfIndex * giftsPerVisit;
                visitCount++;
            }
        }

        // Find the number of the first house with enough gifts
        for (int i = 1; i <= numberOfHouses; i++) {
            if (houses[i] >= minimumNumberOfGifts) {
                return i;
            }
        }
        throw new IllegalStateException("Did not find a solution");
    }

    public long computeResultForPart1WithDivisors() {
        final int minimumNumberOfGifts = Integer.parseInt(input);
        final int giftsPerVisit = 10;
        int maxHouseNumber = minimumNumberOfGifts / giftsPerVisit;
        for (int i = 1; i <= maxHouseNumber ; i++) {
            int numberOfGits = findDivisors(i).stream()
                    .mapToInt(divisor -> divisor * giftsPerVisit)
                    .sum();
            if (numberOfGits >= minimumNumberOfGifts) {
                return i;
            }
        }
        throw new IllegalStateException("Did not find a solution");
    }

    public long computeResultForPart2WithDivisors() {
        final int minimumNumberOfGifts = Integer.parseInt(input);
        final int giftsPerVisit = 11;
        final int maxNumberOfVisitPerElf = 50;

        int maxHouseNumber = minimumNumberOfGifts / giftsPerVisit;
        for (int houseNumber = 1; houseNumber <= maxHouseNumber ; houseNumber++) {
            int finalHouseNumber = houseNumber;
            int numberOfGits = findDivisors(houseNumber).stream()
                    .filter(d -> (finalHouseNumber / d) <= maxNumberOfVisitPerElf)
                    .mapToInt(divisor -> divisor * giftsPerVisit)
                    .sum();
            if (numberOfGits >= minimumNumberOfGifts) {
                return houseNumber;
            }
        }
        throw new IllegalStateException("Did not find a solution");
    }

    public static Set<Integer> findDivisors(int input) {
        if (input == 1) {
            return Sets.newHashSet(1);
        }
        List<Integer> primeFactors = Primes.primeFactors(input);
        Set<List<Integer>> combinations = Sets.newHashSet(generateAllPossibleSubLists(primeFactors));
        combinations.add(Lists.newArrayList(1)); // 1 is not included in prime factors combinations but it is a divisor
        return combinations.stream()
                .map(ints -> ints.stream().reduce(1, (product, i) -> product * i)) // Reduce to product of elements
                .collect(Collectors.toSet());
    }

    public static Set<List<Integer>> generateAllPossibleSubLists(List<Integer> elements) {
        // Generate all possible sublists by:
        // - using a bitmask with each bit representing one element.
        // - enumerate all the possible values for that mask (same as counting from 0 to 2^size -1)
        Set<List<Integer>> subsets = Sets.newHashSet();
        for (int i = 0; i < (1 << elements.size()); i++) {
            subsets.add(extractElementsBasedOnMask(elements, i));
        }
        return subsets;
    }

    private static List<Integer> extractElementsBasedOnMask(List<Integer> elements, int indexBitMask) {
        List<Integer> extract = Lists.newArrayList();
        for (int index = 0; index < elements.size(); index++) {
            // Add element at index n of that bit is on
            if (((indexBitMask >> index) & 1) == 1) {
                extract.add(elements.get(index));
            }
        }
        return extract;
    }
}