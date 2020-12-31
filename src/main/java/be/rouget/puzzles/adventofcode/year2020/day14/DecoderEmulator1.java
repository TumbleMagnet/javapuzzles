package be.rouget.puzzles.adventofcode.year2020.day14;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecoderEmulator1 {

    private String mask;
    private Map<Integer, Long> memories = Maps.newHashMap();

    public long sumOfMemories() {
        return memories.values().stream().mapToLong(Long::longValue).sum();
    }

    public void executeInstruction(String instruction) {
        if (instruction.startsWith("mask")) {
            executeMaskInstruction(instruction);
        } else if (instruction.startsWith("mem")) {
            executeMemInstruction(instruction);
        }
    }

    private void executeMemInstruction(String instruction) {
        // mem[50907] = 468673978
        Pattern pattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
        Matcher matcher = pattern.matcher(instruction);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid instruction " + instruction);
        }
        Integer memoryIndex = Integer.valueOf(matcher.group(1));
        Long value = Long.valueOf(matcher.group(2));
        long maskedValue = maskValue(value, mask);
        memories.put(memoryIndex, maskedValue);
    }

    private void executeMaskInstruction(String instruction) {
        // mask = 0111X10100100X1111X10010X000X1000001
        mask = instruction.substring(7);
        if (mask.length() != 36) {
            throw new IllegalStateException("Extracted invalid mask " + mask + " from instruction " + instruction);
        }
    }

    public static long maskValue(long value, String mask) {
        String binaryValue = Long.toBinaryString(value);
        if (binaryValue.length() >= mask.length()) {
            throw new IllegalArgumentException("Binary value is " + value + " has more bits than the mask");
        }
        binaryValue = StringUtils.leftPad(binaryValue, mask.length(), "0");
        String maskedBinary = maskBinaryValue(binaryValue, mask);
        return Long.parseLong(maskedBinary, 2);
    }

    public static String maskBinaryValue(String value, String mask) {
        if (value.length() != mask.length()) {
            throw new IllegalArgumentException("Value and mask have different lengths");
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char valueChar = value.charAt(i);
            char maskChar = mask.charAt(i);
            if ((maskChar == '0') || (maskChar == '1')) {
                result.append(maskChar);
            } else if (maskChar == 'X') {
                result.append(valueChar);
            }
            else {
                throw new IllegalArgumentException("Invalid character " + maskChar + " at index " + i + " in mask " + mask);
            }
        }
        return result.toString();
    }

}
