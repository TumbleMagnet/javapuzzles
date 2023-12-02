package be.rouget.puzzles.adventofcode.year2023.day01;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Calibration {

    private Calibration() {
    }

    public static int extractCalibrationPart1(String line) {
        List<String> digits = AocStringUtils.extractCharacterList(line).stream()
                .filter(StringUtils::isNumeric)
                .toList();
        String calibrationString = digits.get(0) + digits.get(digits.size() - 1);
        return Integer.parseInt(calibrationString);
    }

    public static int extractCalibrationPart2(String line) {

        List<String> digits = Lists.newArrayList();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                digits.add(String.valueOf(c));
            } else {
                // Verify if we have a digit word like "one"
                String remainingString = line.substring(i);
                Arrays.stream(Digits.values())
                        .filter(digit -> remainingString.startsWith(digit.name().toLowerCase()))
                        .findFirst()
                        .ifPresent(digit -> digits.add(String.valueOf(digit.getValue())));
            }
        }
        String calibrationString = digits.get(0) + digits.get(digits.size() - 1);
        return Integer.parseInt(calibrationString);
    }
    
    
}
