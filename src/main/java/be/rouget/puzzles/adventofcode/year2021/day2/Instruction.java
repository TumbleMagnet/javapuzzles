package be.rouget.puzzles.adventofcode.year2021.day2;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Instruction {

    Command command;
    int value;

    public static Instruction fromInput(String input) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+) (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        Command command = Command.fromInput(matcher.group(1));
        int value = Integer.parseInt(matcher.group(2));
        return new Instruction(command, value);
    }
}
