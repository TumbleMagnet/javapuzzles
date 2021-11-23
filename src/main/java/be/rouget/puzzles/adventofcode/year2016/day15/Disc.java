package be.rouget.puzzles.adventofcode.year2016.day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Disc {
    private int id;
    private int numberOfPositions;
    private int positionAtTimeZero;

    private static final Pattern PATTERN = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");

    public static Disc parse(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("cannot parse input: " + input);
        }
        int id = Integer.parseInt(matcher.group(1));
        int numberOfPositions = Integer.parseInt(matcher.group(2));
        int positionAtTimeZero = Integer.parseInt(matcher.group(3));
        return new Disc(id, numberOfPositions, positionAtTimeZero);
    }

    public int getPositionAtTime(int i) {
        return (positionAtTimeZero + i) % numberOfPositions;
    }

    public Disc(int id, int numberOfPositions, int positionAtTimeZero) {
        this.id = id;
        this.numberOfPositions = numberOfPositions;
        this.positionAtTimeZero = positionAtTimeZero;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfPositions() {
        return numberOfPositions;
    }

    public int getPositionAtTimeZero() {
        return positionAtTimeZero;
    }

    @Override
    public String toString() {
        return "Disc{" +
                "id=" + id +
                ", numberOfPositions=" + numberOfPositions +
                ", positionAtTimeZero=" + positionAtTimeZero +
                '}';
    }

}
