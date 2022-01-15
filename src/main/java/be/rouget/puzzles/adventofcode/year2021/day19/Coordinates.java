package be.rouget.puzzles.adventofcode.year2021.day19;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Coordinates {
    int x, y, z;

    public static Coordinates parse(String input) {
        Pattern pattern = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int z = Integer.parseInt(matcher.group(3));
        return new Coordinates(x, y, z);
    }

    public Coordinates translate(Coordinates newReference) {
        return new Coordinates(x - newReference.getX(), y - newReference.getY(), z - newReference.getZ());
    }

    public Coordinates changeDirection(FacingDirection newDirection) {
        switch (newDirection) {
            case X_POSITIVE: return new Coordinates(x, y, z);
            case X_NEGATIVE: return new Coordinates(-x, -y, z);
            case Y_POSITIVE: return new Coordinates(y, -x, z);
            case Y_NEGATIVE: return new Coordinates(-y, x, z);
            case Z_POSITIVE: return new Coordinates(z, y, -x);
            case Z_NEGATIVE: return new Coordinates(-z, y, x);
            default: throw new IllegalArgumentException("Unsupported direction " + newDirection);
        }
    }

    public Coordinates rotate(Rotation rotation) {
        switch (rotation) {
            case R_000: return new Coordinates(x, y, z);
            case R_090: return new Coordinates(x, z, -y);
            case R_180: return new Coordinates(x, -y, -z);
            case R_270: return new Coordinates(x, -z, y);
            default: throw new IllegalArgumentException("Unsupported rotation " + rotation);
        }
    }

    public Coordinates add(Coordinates other) {
        return new Coordinates(
                x + other.getX(),
                y + other.getY(),
                z + other.getZ()
        );
    }

    public Coordinates minus(Coordinates other) {
        return add(other.inverse());
    }

    public Coordinates inverse() {
        return new Coordinates(-x, -y, -z);
    }

    public int distanceFrom(Coordinates other) {
        Coordinates difference = minus(other);
        return Math.abs(difference.getX()) + Math.abs(difference.getY()) + Math.abs(difference.getZ());
    }
}
