package be.rouget.puzzles.adventofcode.year2016.day9;

import lombok.Value;

@Value
public class Instruction {
    int characters;
    int copies;

    public static Instruction parse(String input) {
        String[] tokens = input.split("x");
        return new Instruction(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
