package be.rouget.puzzles.adventofcode.year2020.day14;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecoderEmulator2 {

    private String mask;
    private Map<Long, Long> memories = Maps.newHashMap();

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
        Long memoryIndex = Long.valueOf(matcher.group(1));
        Long value = Long.valueOf(matcher.group(2));
        List<Long> addresses = maskAddress(memoryIndex);
        for (Long address : addresses) {
            memories.put(address, value);
        }
    }

    private void executeMaskInstruction(String instruction) {
        // mask = 0111X10100100X1111X10010X000X1000001
        mask = instruction.substring(7);
        if (mask.length() != 36) {
            throw new IllegalStateException("Extracted invalid mask " + mask + " from instruction " + instruction);
        }
    }

    public List<Long> maskAddress(long address) {
        String binaryAddress = longToBinary(address, mask.length());
        String maskedBinaryAddress = maskBinaryAddress(binaryAddress);
        return generateAddresses(maskedBinaryAddress);
    }

    public static List<Long> generateAddresses(String maskedBinaryAddress) {

        List<Long> addresses = Lists.newArrayList();

        // Count the numbers of wildchars in address
        int numberOfWildchars = StringUtils.countMatches(maskedBinaryAddress, "X");

        // Generate all binary values for wildchars
        long maxCounter = Math.round(Math.pow(2, numberOfWildchars))-1L;
        for (long i = 0; i <= maxCounter; i++) {
            // Replace each X by the corresponding bit in the counter
            String counterInBinary = longToBinary(i, numberOfWildchars);
            String binaryAddress = maskedBinaryAddress;
            for (int bit = 0; bit < numberOfWildchars; bit++) {
                binaryAddress = binaryAddress.replaceFirst("X", String.valueOf(counterInBinary.charAt(bit)));
            }
            addresses.add(binaryToLong(binaryAddress));
        }
        return addresses;
    }

    private String maskBinaryAddress(String binaryAddress) {
        if (binaryAddress.length() != mask.length()) {
            throw new IllegalArgumentException("Value and mask have different lengths");
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < binaryAddress.length(); i++) {
            char addressChar = binaryAddress.charAt(i);
            char maskChar = mask.charAt(i);

            if ((maskChar == '0')) {
                result.append(addressChar);
            } else if (maskChar == '1') {
                result.append('1');
            } else if (maskChar == 'X') {
                result.append('X');
            }
            else {
                throw new IllegalArgumentException("Invalid character " + maskChar + " at index " + i + " in mask " + mask);
            }
        }
        return result.toString();
    }

    private static String longToBinary(Long value, int numberOfBits) {
        String binaryValue = Long.toBinaryString(value);
        if (binaryValue.length() > numberOfBits) {
            throw new IllegalArgumentException("Binary value is " + value + " has more bits (" + binaryValue.length() + ") than the specified width (" + numberOfBits + ")");
        }
        binaryValue = StringUtils.leftPad(binaryValue, numberOfBits, "0");
        return binaryValue;
    }

    private static Long binaryToLong(String binary) {
        return Long.parseLong(binary, 2);
    }
}
