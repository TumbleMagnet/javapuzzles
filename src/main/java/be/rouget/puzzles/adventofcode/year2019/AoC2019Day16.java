package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AoC2019Day16 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day16.class);
    private static final int[] MASTER_PATTERN = new int[] { 0, 1, 0, -1 };

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day16_input.txt");
        LOG.info("Result is " + Arrays.toString(computeResult2(input, 100)));

/*
        int inputSize = 20;
        int[][] patternMatrix = generatePatternMatrix(inputSize);
        for (int i=0; i<inputSize; i++) {
            System.out.println(Arrays.toString(patternMatrix[i]));
        }
*/
    }

    public static int[] computeResult(String input, int count) {

        int[][] matrix = generatePatternMatrix(input.length());
        int[] digits = toIntArray(input);
        for (int i = 0; i < count; i++) {
            digits = transform(digits, matrix);
            LOG.info("Count: "+ (i+1) + " - " + Arrays.toString(digits));
        }

        return digits;
    }

    public static int[] computeResult2(String input, int count) {
        int offset = 5976683;
        int size = input.length()*10000-offset;
        LOG.info("Need to compute last " + size + " digits...");
        int[] inputDigits = toIntArray(input);
        int[] digits = new int[size];
        // Fill digits from input
        for (int i=0; i<size; i++) {
            digits[size-1-i]=inputDigits[649-(i % 650)];
        }

        // Do 100 transform
        for (int i = 0; i < count; i++) {
            digits = simpleTransform(digits);
        }

        return new int[] {
                digits[0],
                digits[1],
                digits[2],
                digits[3],
                digits[4],
                digits[5],
                digits[6],
                digits[7],
        };
    }

    private static int[] simpleTransform(int[] input) {
        int size = input.length;
        int[] output = new int[input.length];
        int previous = 0;
        for (int i=0; i<input.length; i++) {
            int newDigit = (previous + input[size-1-i]) % 10;
            output[size-1-i] = newDigit;
            previous = newDigit;
        }
        return output;
    }


    private static int[] transform(int[] inputDigits, int[][] matrix) {
        int[] outputDigits = new int[inputDigits.length];
        for (int i=0; i<inputDigits.length; i++) {
            outputDigits[i] = computeDigit(inputDigits, matrix[i]);
        }
        return outputDigits;
    }

    private static int computeDigit(int[] inputDigits, int[] matrix) {
        int addition = 0;
        for (int i=0; i<inputDigits.length; i++) {
            addition += inputDigits[i] * matrix[i];
        }
        int lastDigit = Math.abs(addition) % 10;
        return lastDigit;
    }

    private static int[] toIntArray(String input) {
        int[] array = new int[input.length()];
        int i=0;
        for (char c: input.toCharArray()) {
            array[i] = Character.getNumericValue(c);
            i++;
        }
        return array;
    }

    public static int[][] generatePatternMatrix(int inputLength) {

        int[][] patternMatrix = new int[inputLength][inputLength];
        for (int lineIndex=0; lineIndex < inputLength; lineIndex++) {
            int[] patternLine = generatePatternForDigits(inputLength, lineIndex);
            for (int i=0; i<patternLine.length; i++) {
                patternMatrix[lineIndex][i]= patternLine[i];
            }
        }
        return patternMatrix;
    }

    private static int[] generatePatternForDigits(int inputLength, int digitIndex) {
        List<Integer> pattern = new ArrayList<>();
        int patternIndex = 0;
        boolean skipNext = true;
        while (true) {
            for (int i=0; i<digitIndex+1; i++) {
                if (!skipNext) {
                    pattern.add(MASTER_PATTERN[patternIndex]);
                    if (pattern.size() >= inputLength) {
                        return pattern.stream().mapToInt(Integer::intValue).toArray();
                    }
                }
                else {
                    skipNext = false;
                }
            }
            patternIndex++;
            patternIndex = patternIndex % MASTER_PATTERN.length;
        }
    }
}