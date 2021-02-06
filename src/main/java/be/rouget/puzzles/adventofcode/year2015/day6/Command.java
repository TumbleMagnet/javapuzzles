package be.rouget.puzzles.adventofcode.year2015.day6;

import be.rouget.puzzles.adventofcode.util.map.Position;
import lombok.Data;
import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Command {
    Instruction instruction;
    Position from;
    Position to;

    public static Command fromInput(String input) {
        // turn off 12,823 through 102,934
        Pattern commandPattern = Pattern.compile("(.*) (\\d+,\\d+) through (\\d+,\\d+)");
        Matcher matcher = commandPattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        Instruction instruction = Instruction.fromInput(matcher.group(1));
        Position from = parsePosition(matcher.group(2));
        Position to = parsePosition(matcher.group(3));
        return new Command(instruction, from, to);
    }

    private static Position parsePosition(String input) {
        String[] parts = input.split(",");
        return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
