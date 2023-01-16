package be.rouget.puzzles.adventofcode.year2022.day15;

import be.rouget.puzzles.adventofcode.util.Range;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class BeaconExclusionZone {

    private static final Logger LOG = LogManager.getLogger(BeaconExclusionZone.class);
    public static final int PART1_TARGET_ROW = 2000000;
    public static final int PART2_MAX_COORDINATE = 4000000;
    
    private final List<SensorReport> sensorReports;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(BeaconExclusionZone.class);
        BeaconExclusionZone aoc = new BeaconExclusionZone(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
        LOG.info("Result for part 2 (optimized) is: {}", aoc.computeResultForPart2Optimized());
    }

    public BeaconExclusionZone(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        sensorReports = input.stream()
                .map(SensorReport::parse)
                .toList();
    }

    public long computeResultForPart1() {

        // Compute size of exclusion zone
        final List<Range> exclusionZone = computeBeaconExclusionZoneForRow(PART1_TARGET_ROW);
        int sizeOfExclusionZone = exclusionZone.stream()
                .mapToInt(Range::getLength)
                .sum();
        LOG.info("sizeOfExclusionZone={}", sizeOfExclusionZone);

        // Count known beacons on that row and in the exclusion zone
        long numberOfIncludedBeacons = sensorReports.stream()
                .map(SensorReport::closestBeacon)
                .filter(position -> position.getY() == PART1_TARGET_ROW)
                .collect(Collectors.toSet()).stream() // Remove each beacon only once (same beacon could be seen by multiple sensors)
                .mapToInt(Position::getX)
                .filter(x -> exclusionZone.stream().anyMatch(r -> r.contains(x)))
                .count();
        LOG.info("numberOfIncludedBeacons={}", numberOfIncludedBeacons);

        // Result is size of exclusion zone minus the number of included beacons
        return sizeOfExclusionZone - numberOfIncludedBeacons;
    }

    private List<Range> computeBeaconExclusionZoneForRow(int targetRow) {
        List<Range> excludedRanges = sensorReports.stream()
                .map(sensorReport -> sensorReport.computeBeaconExclusionZoneForRow(targetRow))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<Range> mergedExcludedRanges = List.of();
        for (Range range : excludedRanges) {
            mergedExcludedRanges = mergeRange(mergedExcludedRanges, range);
        }
        return mergedExcludedRanges;
    }

    public static List<Range> mergeRange(List<Range> existing, Range newRange) {

        List<Range> nonIntersectingRanges = existing.stream()
                .filter(existingRange -> !existingRange.intersects(newRange))
                .toList();

        List<Range> intersectingRanges = existing.stream()
                .filter(existingRange -> existingRange.intersects(newRange))
                .toList();
        Range mergedRange = newRange;
        for (Range intersectingRange : intersectingRanges) {
            mergedRange = mergedRange.merge(intersectingRange);
        }

        List<Range> result = Lists.newArrayList(nonIntersectingRanges);
        result.add(mergedRange);
        return result;
    }

    public long computeResultForPart2() {

        // Find beacon position by scanning all rows and finding the first row with exactly one possible position 
        // For each row, merge the sensors exclusion zones and remove them from the search area
        Position beaconPosition = null;
        for (int y = 0; y < PART2_MAX_COORDINATE + 1; y++) {
            List<Range> possibleRanges = List.of(new Range(0, PART2_MAX_COORDINATE));
            List<Range> excludedRanges = computeBeaconExclusionZoneForRow(y);
            for (Range excludedRange : excludedRanges) {
                possibleRanges = minusRange(possibleRanges, excludedRange);
            }
            if (possibleRanges.size() == 1) {
                Range onlyRange = possibleRanges.get(0);
                if (onlyRange.getLength() == 1) {
                    beaconPosition = new Position(onlyRange.from(), y);
                    LOG.info("Found beacon position: {}", beaconPosition);
                    break;
                } else {
                    throw new IllegalStateException("Found multiple possible solutions for row " + y);
                } 
            } else if (possibleRanges.size() > 1) {
                throw new IllegalStateException("Found multiple possible solutions for row " + y);
            }
        }
        if (beaconPosition == null) {
            throw new IllegalArgumentException("Did not find the possible beacon position!");
        }
        
        // Return tuning frequency of beacon position    
        return tuningFrequency(beaconPosition);
    }

    @SuppressWarnings("java:S1905")
    private static long tuningFrequency(Position beaconPosition) {
        return (long) beaconPosition.getX() * (long) PART2_MAX_COORDINATE + (long) beaconPosition.getY();
    }

    public List<Range> minusRange(List<Range> startingRanges, Range rangeToExclude) {
        return startingRanges.stream()
                .map(startingRange -> startingRange.minus(rangeToExclude))
                .flatMap(List::stream)
                .toList();
    }

    public long computeResultForPart2Optimized() {
        
        // We can take advantage from the fact there exists only one possible position in the search space.
        // It means that the position we search is necessarily on the outer ring of a zone covered a sensor.
        Position position = sensorReports.stream()
                .map(this::scanOuterRing)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst().orElseThrow();

        // Return tuning frequency of beacon position    
        return tuningFrequency(position);
    }

    private Optional<Position> scanOuterRing(SensorReport sensorReport) {

        Position sensorPosition = sensorReport.sensor();
        int sensorRange = sensorPosition.computeManhattanDistance(sensorReport.closestBeacon());
        
        // Upper half
        for (int y = sensorPosition.getY() - sensorRange; y <= sensorPosition.getY(); y++) {
            Range range = sensorReport.computeBeaconExclusionZoneForRow(y).orElseThrow();
            Position leftPosition = new Position(range.from(), y - 1);
            if (isSolution(leftPosition)) {
                return Optional.of(leftPosition);
            }
            Position rightPosition = new Position(range.to(), y - 1);
            if (isSolution(rightPosition)) {
                return Optional.of(rightPosition);
            }
        }
        
        // Lower half
        for (int y = sensorPosition.getY(); y <= sensorPosition.getY() + sensorRange; y++) {
            Range range = sensorReport.computeBeaconExclusionZoneForRow(y).orElseThrow();
            Position leftPosition = new Position(range.from(), y + 1);
            if (isSolution(leftPosition)) {
                return Optional.of(leftPosition);
            }
            Position rightPosition = new Position(range.to(), y + 1);
            if (isSolution(rightPosition)) {
                return Optional.of(rightPosition);
            }
        }

        // Sensor row
        Position leftPosition = new Position(sensorPosition.getX() - sensorRange - 1, sensorPosition.getY());
        if (isSolution(leftPosition)) {
            return Optional.of(leftPosition);
        }
        Position rightPosition = new Position(sensorPosition.getX() + sensorRange + 1, sensorPosition.getY());
        if (isSolution(rightPosition)) {
            return Optional.of(rightPosition);
        }

        // No match on outer ring of this sensor
        return Optional.empty();
    }

    private boolean isSolution(Position candidate) {

        if (candidate.getX() < 0 || candidate.getX() > PART2_MAX_COORDINATE
                || candidate.getY() < 0 || candidate.getY() > PART2_MAX_COORDINATE) {
            // Not in search area
            return false;
        }
        
        for (SensorReport sensorReport : sensorReports) {
            int sensorRange = sensorReport.sensor().computeManhattanDistance(sensorReport.closestBeacon());
            int distanceToCandidate = sensorReport.sensor().computeManhattanDistance(candidate);
            if (distanceToCandidate <= sensorRange) {
                // Too close to a sensor
                return false;
            }
        }
        
        // Far enough of all sensors
        return true;
    }

}