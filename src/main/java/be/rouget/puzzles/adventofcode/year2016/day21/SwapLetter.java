package be.rouget.puzzles.adventofcode.year2016.day21;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class SwapLetter implements ScramblingFunction {
    String letter1;
    String letter2;

    @Override
    public String scramble(String input) {
        return AocStringUtils.extractCharacterList(input).stream()
                .map(this::swap)
                .collect(Collectors.joining());
    }

    @Override
    public String unScramble(String input) {
        return scramble(input);
    }

    private String swap(String character) {
        if (letter1.equals(character)) {
            return letter2;
        }
        if (letter2.equals(character)) {
            return letter1;
        }
        return character;
    }

    public static ScramblingFunction parse(String input) {
        Pattern pattern = Pattern.compile("swap letter (.) with letter (.)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        String letter1 = matcher.group(1);
        String letter2 = matcher.group(2);
        return new SwapLetter(letter1, letter2);
    }

}
