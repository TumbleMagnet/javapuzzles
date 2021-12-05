package be.rouget.puzzles.adventofcode.year2021.day5;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class VentLine {
    Position from;
    Position to;

    public boolean isVertical() {
        return from.getX() == to.getX();
    }

    public boolean isHorizontal() {
        return from.getY() == to.getY();
    }

    public List<Position> toPoints() {
        int stepX = computeStep(from.getX(), to.getX());
        int stepY = computeStep(from.getY(), to.getY());
        Position current = new Position(from.getX(), from.getY());
        List<Position> points = Lists.newArrayList();
        while (true) {
            points.add(current);
            if (current.equals(to)) {
                break;
            }
            current = new Position(current.getX() + stepX, current.getY() + stepY);
        }
        return points;
    }

    private int computeStep(int start, int end) {
        if (start == end) {
            return 0;
        } else if (start < end) {
            return 1;
        } else {
            return -1;
        }
    }

    public static VentLine fromInput(String input) {
        Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int fromX = Integer.parseInt(matcher.group(1));
        int fromY = Integer.parseInt(matcher.group(2));
        int toX = Integer.parseInt(matcher.group(3));
        int toY = Integer.parseInt(matcher.group(4));
        return new VentLine(new Position(fromX, fromY), new Position(toX, toY));
    }
}
