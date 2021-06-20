package be.rouget.puzzles.adventofcode.year2016.day10;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class InputInstruction implements Instruction {
    public static final Pattern PATTERN = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    int inputValue;
    int destinationBot;

    @Override
    public InstructionType getInstructionType() {
        return InstructionType.INPUT_INSTRUCTION;
    }

    public static InputInstruction parseFromInput(String input) {
        // value 23 goes to bot 208
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        int inputValue = Integer.parseInt(matcher.group(1));
        int destinationBot = Integer.parseInt(matcher.group(2));
        return new InputInstruction(inputValue, destinationBot);
    }
}
