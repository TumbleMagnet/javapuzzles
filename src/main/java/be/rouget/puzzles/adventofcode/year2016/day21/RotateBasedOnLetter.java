package be.rouget.puzzles.adventofcode.year2016.day21;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class RotateBasedOnLetter implements ScramblingFunction {
    String letter;

    @Override
    public String scramble(String input) {
        int index = input.indexOf(letter);
        int steps = computeStepsFromIndex(index);
        return Rotate.rotate(input, Direction.RIGHT, steps);
    }

    private int computeStepsFromIndex(int index) {
        return 1 + index + (index >= 4 ? 1 : 0);
    }

    @Override
    public String unScramble(String input) {
        // Rotate left until output can be scrambled back to input
        String output = input;
        while (true) {
            output = Rotate.rotate(output, Direction.LEFT, 1);
            if (scramble(output).equals(input)) {
                return output;
            }
        }
    }

    public static ScramblingFunction parse(String input) {
        Pattern pattern = Pattern.compile("rotate based on position of letter (.)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        String letter = matcher.group(1);
        return new RotateBasedOnLetter(letter);
    }

}
