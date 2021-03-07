package be.rouget.puzzles.adventofcode.year2015.day14;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Reindeer {
    String name;
    int speed;      // In kilometers/second
    int maxFlyTime; // In seconds
    int restTime;   // In seconds

    public static Reindeer fromInput(String input) {
        // Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.
        Pattern pattern = Pattern.compile("(.*) can fly (.*) km/s for (.*) seconds, but then must rest for (.*) seconds\\.");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse: " + input);
        }
        String name = matcher.group(1);
        int speed = Integer.parseInt(matcher.group(2));
        int maxFlyTime = Integer.parseInt(matcher.group(3));
        int restTime = Integer.parseInt(matcher.group(4));
        return new Reindeer(name, speed, maxFlyTime, restTime);
    }

    public int computeDistance(int duration) {
        int distancePerFlight = speed * maxFlyTime;
        int cycleDuration = maxFlyTime + restTime;
        int distanceForCompleteCycles = (duration / cycleDuration) * distancePerFlight;
        int remainingSeconds = duration % cycleDuration;
        int remainingDistance = speed * Math.min(maxFlyTime, remainingSeconds);
        return distanceForCompleteCycles + remainingDistance;
    }
}
