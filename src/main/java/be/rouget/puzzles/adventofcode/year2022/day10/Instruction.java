package be.rouget.puzzles.adventofcode.year2022.day10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Instruction(InstructionCode code, Integer value) {

    public static Instruction parse(String input) {
        if ("noop".equals(input)) {
            return new Instruction(InstructionCode.NOOP, null);
        } else {
            // Parse addx instruction
            Pattern pattern = Pattern.compile("addx (.*)");
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Cannot parse input: " + input);
            }
            int value = Integer.parseInt(matcher.group(1));
            return new Instruction(InstructionCode.ADDX, value);
        }
    }
}
