package be.rouget.puzzles.adventofcode.year2020.day12;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Instruction {
    Action action;
    Integer value;

    public static Instruction fromInput(String inputLine) {
        Pattern pattern = Pattern.compile("(\\w)(\\d+)");
        Matcher matcher = pattern.matcher(inputLine);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + inputLine);
        }
        String actionCode = matcher.group(1);
        Action action = Action.fromCode(actionCode);
        Integer value = Integer.parseInt(matcher.group(2));
        return new Instruction(action, value);
    }
}
