package be.rouget.puzzles.adventofcode.year2016.day21;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Reverse implements ScramblingFunction {
    int index1;
    int index2;

    @Override
    public String scramble(String input) {

        List<String> inputCharacters = AocStringUtils.extractCharacterList(input);
        List<String> prefix = index1 > 0 ? inputCharacters.subList(0, index1) : Lists.newArrayList();
        List<String> reversed = inputCharacters.subList(index1, index2+1);
        Collections.reverse(reversed);
        List<String> suffix = index2 < inputCharacters.size() -1? inputCharacters.subList(index2 + 1, inputCharacters.size()) : Lists.newArrayList();

        List<String> output = Lists.newArrayList(prefix);
        output.addAll(reversed);
        output.addAll(suffix);
        return AocStringUtils.join(output);
    }

    @Override
    public String unScramble(String input) {
        return scramble(input);
    }

    public static ScramblingFunction parse(String input) {
        Pattern pattern = Pattern.compile("reverse positions (\\d+) through (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        int index1 = Integer.parseInt(matcher.group(1));
        int index2 = Integer.parseInt(matcher.group(2));
        return new Reverse(index1, index2);
    }
}
