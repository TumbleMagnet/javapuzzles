package be.rouget.puzzles.adventofcode.year2022.day15;

import be.rouget.puzzles.adventofcode.util.Range;
import be.rouget.puzzles.adventofcode.util.map.Position;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record SensorReport(Position sensor, Position closestBeacon) {

    public static SensorReport parse(String input) {
        Pattern pattern = Pattern.compile("Sensor at x=(.+), y=(.+): closest beacon is at x=(.+), y=(.+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int sensorX = Integer.parseInt(matcher.group(1));
        int sensorY = Integer.parseInt(matcher.group(2));
        int beaconX = Integer.parseInt(matcher.group(3));
        int beaconY = Integer.parseInt(matcher.group(4));
        return new SensorReport(new Position(sensorX, sensorY), new Position(beaconX, beaconY));
    }

    public Optional<Range> computeBeaconExclusionZoneForRow(int rowY) {
        int beaconDistance = sensor().computeManhattanDistance(closestBeacon());
        Position projectionOfSensorOnTargetRow = new Position(sensor().getX(), rowY);
        int distanceOfProjection = sensor().computeManhattanDistance(projectionOfSensorOnTargetRow);
        if (distanceOfProjection > beaconDistance) {
            // Row is too far, beacon exclusion zone is empty
            return Optional.empty();
        }
        else {
            int remainingDistance = Math.abs(beaconDistance - distanceOfProjection);
            return Optional.of(new Range(projectionOfSensorOnTargetRow.getX() - remainingDistance, projectionOfSensorOnTargetRow.getX() + remainingDistance));
        }
    }

}
