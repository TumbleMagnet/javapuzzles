package be.rouget.puzzles.adventofcode.year2024.day14;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {
    private Coordinates coordinates;
    private final Velocity velocity;

    public Robot(Coordinates coordinates, Velocity velocity) {
        this.coordinates = coordinates;
        this.velocity = velocity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public static Robot parse(String input) {
        // p=82,15 v=-7,-48
        Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int deltaX = Integer.parseInt(matcher.group(3));
        int deltaY = Integer.parseInt(matcher.group(4));
        return new Robot(new Coordinates(x, y), new  Velocity(deltaX, deltaY));
    }

    public void move(Room room) {
        Coordinates newCoordinatesBeforeWrapping = new Coordinates(
                coordinates.x() + velocity.deltaX(),
                coordinates.y() + velocity.deltaY()
        );
        this.coordinates = room.wrapCoordinates(newCoordinatesBeforeWrapping);
    }
}
