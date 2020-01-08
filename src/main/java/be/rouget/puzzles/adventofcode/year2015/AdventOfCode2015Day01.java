package be.rouget.puzzles.adventofcode.year2015;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class AdventOfCode2015Day01 {

    public static int computeTargetFloor(String input) {

        int floor = 0;

        floor += StringUtils.countMatches(input, '(');
        floor -= StringUtils.countMatches(input, ')');
        return floor;
    }

    public static int computeIndexOfFirstBasement(String input) {

        int floor = 0;
        int charIndex = 1;
        char[] inputChars = input.toCharArray();
        for (char c: inputChars) {
            if ('(' == c ) {
                floor++;
            }
            else if (')' == c) {
                floor--;
            }
            if (floor == -1) {
                return charIndex;
            }
            charIndex++;
        }

        throw new IllegalStateException("Finished input without going to the basement.");
    }


    public static void main(String[] args) {
        System.out.println("Starting puzzle...");

        try {
            String input = FileUtils.readFileToString(new File("C:\\programming\\projects\\puzzles\\src\\main\\resources\\aoc_2015_day01_input.txt"), Charset.defaultCharset());
            System.out.println("Floor is: " + AdventOfCode2015Day01.computeTargetFloor(input));
            System.out.println("Index of first basement is: " + AdventOfCode2015Day01.computeIndexOfFirstBasement(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
