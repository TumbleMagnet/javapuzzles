package be.rouget.puzzles.adventofcode.year2023.day06;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.LongStream;


public class WaitForIt {

    private static final Logger LOG = LogManager.getLogger(WaitForIt.class);
    
    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        WaitForIt aoc = new WaitForIt();
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        List<RaceRecord> races = List.of(
                new RaceRecord(51L, 222L),
                new RaceRecord(92L, 2031L),
                new RaceRecord(68L, 1126L),
                new RaceRecord(90L, 1225L)
        );

        return races.stream()
                .mapToLong(this::countNumberOfWaysToWin)
                .reduce(1L, (l1, l2) -> l1 * l2);
    }

    private long countNumberOfWaysToWin(RaceRecord race) {
        return LongStream.rangeClosed(0, race.durationInMilliseconds())
                .map(holdDuration -> computeDistance(race.durationInMilliseconds(), holdDuration))
                .filter(distance -> distance > race.recordDistanceInMillimeters())
                .count();
    }
    
    public static long computeDistance(long raceDurationInMilliseconds, long holdDurationInMilliseconds) {
        long speed = holdDurationInMilliseconds;
        return speed * (raceDurationInMilliseconds - holdDurationInMilliseconds);
    }
    
    public long computeResultForPart2() {
        // Brute force runs in about 120 ms.
        // Other options could have been:
        // - solve it using maths (it is a quadratic equation)
        // - optimize by finding the 2 crossing points using a binary search
        RaceRecord race = new RaceRecord(51926890L, 222203111261225L);
        return countNumberOfWaysToWin(race);
    }
}