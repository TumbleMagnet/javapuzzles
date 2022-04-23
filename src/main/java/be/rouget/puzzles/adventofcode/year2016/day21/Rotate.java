package be.rouget.puzzles.adventofcode.year2016.day21;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import lombok.Value;

import java.util.ArrayDeque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Rotate implements ScramblingFunction {
    Direction direction;
    int steps;

    @Override
    public String scramble(String input) {
        return rotate(input, direction, steps);
    }

    @Override
    public String unScramble(String input) {
        return new Rotate(direction.getOpposite(), steps).scramble(input);
    }

    public static String rotate(String input, Direction direction, int steps) {
        String output = input;
        for (int i = 0; i < steps; i++) {
            output = rotateOnce(output, direction);
        }
        return output;
    }

    public static String rotateOnce(String input, Direction direction) {
        List<String> inputChars = AocStringUtils.extractCharacterList(input);
        ArrayDeque<String> deque = new ArrayDeque<>(inputChars);
        if (Direction.LEFT == direction) {
            String character = deque.removeFirst();
            deque.addLast(character);
        } else if (Direction.RIGHT == direction) {
            String character = deque.removeLast();
            deque.addFirst(character);
        } else {
            throw new IllegalStateException("Unsupported direction: " + direction);
        }
        return AocStringUtils.join(deque);
    }

    public static ScramblingFunction parse(String input) {
        Pattern pattern = Pattern.compile("rotate (left|right) (\\d+) step(s?)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        Direction direction = Direction.valueOf(matcher.group(1).toUpperCase());
        int steps = Integer.parseInt(matcher.group(2));
        return new Rotate(direction, steps);
    }

}
