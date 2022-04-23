package be.rouget.puzzles.adventofcode.util;

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
}
