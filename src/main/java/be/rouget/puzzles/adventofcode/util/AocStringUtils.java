package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AocStringUtils {
    
    public static List<String> extractCharacterList(String line) {
        String[] lineChars = splitCharacters(line);
        return Arrays.asList(lineChars);
    }

    public static String[] splitCharacters(String line) {
        return line.split("(?!^)");
    }

    public static String join(Collection<String> parts) {
        return String.join("", parts);
    }

    public static List<List<String>> extractParagraphs(List<String> input) {
        List<List<String>> paragraphs = Lists.newArrayList();
        List<String> currentParagraph = Lists.newArrayList();
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                paragraphs.add(currentParagraph);
                currentParagraph = Lists.newArrayList();
            } else {
                currentParagraph.add(line);
            }
        }
        if (!currentParagraph.isEmpty()) {
            paragraphs.add(currentParagraph);
        }
        return paragraphs;
    }

    public static List<String> readLines(String input) {
        return Arrays.asList(input.split("\\R"));
    }

    private AocStringUtils() {
    }
}
