package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AoC2019Day04 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day04.class);


    public static boolean isValidPassword(int number) {

        String numberString = String.valueOf(number);
        char[] chars = numberString.toCharArray();

        // 2 consecutive digits are the same but are not part of a larger group
//        boolean hasConsecutiveDigits = numberString.contains("11")
//            || numberString.contains("22")
//            || numberString.contains("33")
//            || numberString.contains("44")
//            || numberString.contains("55")
//            || numberString.contains("66")
//            || numberString.contains("77")
//            || numberString.contains("88")
//            || numberString.contains("99")
//            || numberString.contains("00");
//        if (!hasConsecutiveDigits) {
//            return false;
//        }

        // 2 consecutive digits are the same but are not part of a larger group

        Character previousChar = null;
        int sequenceCount = 0;
        for (char c: chars)  {
            int currentDigit = Character.getNumericValue(c);
            if (previousChar != null) {
                if (previousChar.compareTo(c) == 0) {
                    sequenceCount++;
                }
                else {
                    if (sequenceCount == 2) {
                        break;
                    }
                    sequenceCount = 1;
                }
            }
            else {
                sequenceCount++;
            }
            previousChar = c;
        }
        if (sequenceCount != 2) {
            return false;
        }

        // digits never decrease
        int previousDigit = -1;
        for (char c: chars)  {
            int currentDigit = Character.getNumericValue(c);
            if (previousDigit != -1) {
                if (currentDigit < previousDigit) {
                    return false;
                }
            }
            previousDigit = Character.getNumericValue(c);
        }

        return true;
    }

    public static void main(String[] args) {
        LOG.info("Starting...");
        long count = IntStream.rangeClosed(109165,576723).filter(AoC2019Day04::isValidPassword).count();
        LOG.info("Number of passwords: " + count);

        "".split("");
    }

 }