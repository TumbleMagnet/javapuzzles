package be.rouget.puzzles.adventofcode.year2020.day8;

import lombok.Value;

@Value
public class Instruction {
    InstructionCode code;
    int value;

    public static Instruction parse(String line) {
        String[] tokens = line.split(" ");
        InstructionCode code = InstructionCode.fromCode(tokens[0]);
        int value = Integer.parseInt(tokens[1]);
        return new Instruction(code, value);
    }
}
