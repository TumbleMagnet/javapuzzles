package be.rouget.puzzles.adventofcode.year2015.day14;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReindeerOlympics {

    private static final String YEAR = "2015";
    private static final String DAY = "14";

    private static final Logger LOG = LogManager.getLogger(ReindeerOlympics.class);

    public static final int RACE_DURATION = 2503;

    private final List<String> input;
    private final List<Reindeer> reindeers;

    public ReindeerOlympics(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        this.reindeers = input.stream().map(Reindeer::fromInput)
                .peek(r -> LOG.info(r))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ReindeerOlympics aoc = new ReindeerOlympics(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return reindeers.stream()
                .mapToInt(r -> r.computeDistance(RACE_DURATION))
                .max()
                .orElseThrow(() -> new IllegalStateException("No reindeers!"));
    }
    public long computeResultForPart2() {

        List<RaceStatus> statusList = reindeers.stream()
                .map(r -> new RaceStatus(r, 0, 0))
                .collect(Collectors.toList());

        for (int raceTime = 1; raceTime <= RACE_DURATION; raceTime++) {

            // Compute the current distance of each reindeer and the maximum distance
            int maxDistance = 0;
            for (RaceStatus status : statusList) {
                status.updateDistance(raceTime);
                maxDistance = Math.max(maxDistance, status.getDistance());
            }

            // Award a point to the leading reindeers
            for (RaceStatus status : statusList) {
                if (status.getDistance() == maxDistance) {
                    status.addOnePoint();
                }
            }
        }

        // Return the points of the winning reindeer
        return statusList.stream()
                .mapToInt(RaceStatus::getScore)
                .max()
                .orElseThrow(() -> new IllegalStateException("No winning deer!"));
    }
}