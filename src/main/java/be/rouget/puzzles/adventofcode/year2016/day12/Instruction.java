package be.rouget.puzzles.adventofcode.year2016.day12;

import com.google.common.collect.Lists;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

@Value
public class Instruction {
    InstructionCode code;
    List<String> arguments;

    public static Instruction parseInstruction(String input) {
        String[] words = input.split(" ");
        InstructionCode code = InstructionCode.parse(words[0]);
        List<String> arguments = Lists.newArrayList(Arrays.copyOfRange(words, 1, words.length));
        return new Instruction(code, arguments);
    }
}
