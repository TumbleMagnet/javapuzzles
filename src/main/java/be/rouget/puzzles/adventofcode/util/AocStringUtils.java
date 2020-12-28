package be.rouget.puzzles.adventofcode.util;

import java.util.Arrays;
import java.util.List;

public class AocStringUtils {
    public static List<String> extractCharacterList(String line) {
        String[] lineChars = splitCharacters(line);
        List<String> collection = Arrays.asList(lineChars);
        return collection;
    }

    public static String[] splitCharacters(String line) {
        return line.split("(?!^)");
    }
}
