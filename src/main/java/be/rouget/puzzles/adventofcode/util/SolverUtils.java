package be.rouget.puzzles.adventofcode.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverUtils {

    public static List<String> readInput(Class<?> clazz) {
        return readInput(clazz, "input");
    }

    public static List<String> readTest(Class<?> clazz) {
        return readInput(clazz, "test");
    }

    public static List<String> readInput(Class<?> clazz, String suffix) {
        Pattern pattern = Pattern.compile("be.rouget.puzzles.adventofcode.year(\\d+).day(\\d+)..*");
        String canonicalClassName = clazz.getCanonicalName();
        Matcher matcher = pattern.matcher(canonicalClassName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse the class canonical name: " + canonicalClassName);
        }
        String year = matcher.group(1);
        String day = matcher.group(2);
        return ResourceUtils.readLines(year + "/aoc_" + year + "_day" + day + "_" + suffix + ".txt");
    }
}
