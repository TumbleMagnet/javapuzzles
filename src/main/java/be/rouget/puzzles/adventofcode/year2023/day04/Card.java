package be.rouget.puzzles.adventofcode.year2023.day04;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Card(int index, List<Integer> winningNumbers, List<Integer> cardNumbers) {

    public Set<Number> extractWinningNumbersOnCard() {
        Set<Integer> winningSet = Sets.newHashSet(winningNumbers);
        Set<Integer> cardSet = Sets.newHashSet(cardNumbers);
        return SetUtils.intersection(winningSet, cardSet);
    }

    public int countWinningNumbersOnCard() {
        return extractWinningNumbersOnCard().size();
    }

    public int computePoints() {
        int numberOfWiningNumbersOnCard = countWinningNumbersOnCard();
        if (numberOfWiningNumbersOnCard == 0) {
            return 0;
        }
        return BigInteger.valueOf(2).pow(numberOfWiningNumbersOnCard -1).intValue();
    }
    
    public static Card parse(String input) {
        // Card   1: 33 34 29 52 91  7 31 42  2  6 | 53 52  6 96 42 91  2 23  7 38 90 28 31 51  1 26 33 22 95 34 29 77 32 86  3
        Pattern globalPattern = Pattern.compile("Card ( *)(\\d*): (.*) \\| (.*)");
        Matcher matcher = globalPattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int index = Integer.parseInt(matcher.group(2));
        List<Integer> winningNumbers = extractNumbers(matcher.group(3));
        List<Integer> cardNumbers = extractNumbers(matcher.group(4));
        return new Card(index, winningNumbers, cardNumbers);
    }

    private static List<Integer> extractNumbers(String input) {
        // 33 34 29 52 91  7 31 42  2  6
        String normalizedInput = StringUtils.normalizeSpace(input);
        String[] tokens = normalizedInput.split(" ");
        return Arrays.stream(tokens)
                .map(Integer::parseInt)
                .toList();
    }
}
