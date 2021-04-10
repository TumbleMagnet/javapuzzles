package be.rouget.puzzles.adventofcode.year2015.day23;

import lombok.Value;

@Value
public class Instruction {
    InstructionCode code;
    Register register;
    Integer offset;

    public static Instruction fromInput(String line) {
        String[] tokens = line.split(" ");
        InstructionCode code = InstructionCode.valueOf(tokens[0].toUpperCase());
        switch (code) {
            case HLF:
            case TPL:
            case INC:
                return new Instruction(code, register(tokens[1]), null);
            case JMP:
                return new Instruction(code, null, offset(tokens[1]));
            case JIE:
            case JIO:
                return new Instruction(code, register(tokens[1]), offset(tokens[2]));
            default:
                throw new IllegalArgumentException("Invalid instruction input: " + line);
        }
    }

    private static Register register(String token) {
        return Register.valueOf(token.replace(",", "").toUpperCase());
    }

    private static int offset(String token) {
        return Integer.parseInt(token.replace("+", ""));
    }
}
