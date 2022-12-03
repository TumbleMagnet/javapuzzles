package be.rouget.puzzles.adventofcode.year2016.day22;

import be.rouget.puzzles.adventofcode.util.map.Position;
import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Node {

    public static final Pattern NODE_PATTERN = Pattern.compile("(\\S*)(\\s+)(\\d+)T(\\s+)(\\d+)T(\\s+)(\\d+)T(\\s+)(\\d+)%");

    String name;
    int size;
    int used;
    Position position;

    public int getAvailable() {
        return size - used;
    }

    public boolean isConnectedTo(Node another) {
        return this.getPosition().computeManhattanDistance(another.getPosition()) == 1;
    }

    public static Node parse(String input) {

        // Filesystem              Size  Used  Avail  Use%
        // /dev/grid/node-x0-y0     91T   71T    20T   78%

        Matcher matcher = NODE_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }

        String name = matcher.group(1);
        int size = Integer.parseInt(matcher.group(3));
        int used = Integer.parseInt(matcher.group(5));
        int available = Integer.parseInt(matcher.group(7));
        if (size - used != available) {
            throw new IllegalArgumentException("Invalid sizes: Size: " + size + " Used: " + used + " Available: " + available);
        }
        Position position = parsePosition(name);
        return new Node(name, size, used, position);
    }

    private static Position parsePosition(String name) {
        // /dev/grid/node-x0-y0
        Pattern pattern = Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+)");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse name: " + name);
        }
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        return new Position(x, y);
    }

}
