package be.rouget.puzzles.adventofcode.year2024.day11;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PlutonianPebbles {

    private static final Logger LOG = LogManager.getLogger(PlutonianPebbles.class);
    private final List<Long> stones;
    private final Map<CacheKey, Long> resultCache = Maps.newHashMap();

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PlutonianPebbles.class);
        PlutonianPebbles aoc = new PlutonianPebbles(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public PlutonianPebbles(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        String inputValue = input.getFirst();
        String[] tokens = inputValue.split(" ");
        stones = Arrays.stream(tokens)
                .map(Long::parseLong)
                .toList();
        LOG.info("Found {} stones...", stones.size());
    }

    public long computeResultForPart1() {
        return stones.stream()
                .mapToLong(stone -> countStones(stone, 25))
                .sum();
    }

    public long computeResultForPart2() {
        return stones.stream()
                .mapToLong(stone -> countStones(stone, 75))
                .sum();
    }

    public long countStones(long stone, int blinkCount) {
        if (blinkCount == 0) {
            return 1L;
        }

        // If result has already been computed before, return the known result from the cache
        CacheKey cacheKey = new CacheKey(stone, blinkCount);
        Long cachedResult = resultCache.get(cacheKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        // Compute the result recursively
        List<Long> newStones = blinkStone(stone);
        long result = newStones.stream()
                .mapToLong(newStone -> countStones(newStone, blinkCount - 1))
                .sum();

        // Cache the result for re-use
        resultCache.put(cacheKey, result);

        return result;
    }

    public static List<Long> blinkStone(long stone) {
        // If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
        if (stone == 0L) {
            return List.of(1L);
        }

        // If the stone is engraved with a number that has an even number of digits, it is replaced by two stones.
        // The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved
        // on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
        String stoneAsString = String.valueOf(stone);
        if (stoneAsString.length() % 2 == 0) {
            final int mid = stoneAsString.length() / 2;
            return List.of(
                    Long.parseLong(stoneAsString.substring(0, mid)),
                    Long.parseLong(stoneAsString.substring(mid))
            );
        }

        // If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024
        // is engraved on the new stone.
        long newStone = stone * 2024L;
        return List.of(newStone);
    }

    public record CacheKey(long stone, int blinkCount) {}
}