package be.rouget.puzzles.adventofcode.year2021.day24;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class Instruction {
    InstructionCode code;
    String value1;
    String value2;

    public static Instruction fromInput(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("Invalid blank input");
        }
        String[] tokens = input.split(" ");
        if (!(tokens.length == 2 || tokens.length == 3)) {
            throw new IllegalArgumentException("Invalid number of tokens [" + tokens.length + "] in input: " + input);
        }
        InstructionCode code = InstructionCode.fromKeyword(tokens[0]);
        String value1 = tokens[1];
        String value2 = tokens.length == 3 ? tokens[2] : null;
        return new Instruction(code, value1, value2);
    }
}
