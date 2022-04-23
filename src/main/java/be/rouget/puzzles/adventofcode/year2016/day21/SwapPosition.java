package be.rouget.puzzles.adventofcode.year2016.day21;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class SwapPosition implements ScramblingFunction {
    int index1;
    int index2;

    @Override
    public String scramble(String input) {
        List<String> inputCharacters = AocStringUtils.extractCharacterList(input);
        List<String> outputCharacters = Lists.newArrayList(inputCharacters);
        outputCharacters.set(index1, inputCharacters.get(index2));
        outputCharacters.set(index2, inputCharacters.get(index1));
        return AocStringUtils.join(outputCharacters);
    }

    @Override
    public String unScramble(String input) {
        return scramble(input);
    }


    public static ScramblingFunction parse(String input) {
        Pattern pattern = Pattern.compile("swap position (\\d+) with position (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        int index1 = Integer.parseInt(matcher.group(1));
        int index2 = Integer.parseInt(matcher.group(2));
        return new SwapPosition(index1, index2);
    }

}
