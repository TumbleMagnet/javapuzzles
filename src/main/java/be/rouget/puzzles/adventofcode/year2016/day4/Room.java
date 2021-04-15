package be.rouget.puzzles.adventofcode.year2016.day4;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class Room {
    String encryptedName;
    int sectorId;
    String checksum;

    public boolean isValid() {
        return checksum.equals(computeChecksum());
    }

    private String computeChecksum() {
        // the checksum is the five most common letters in the encrypted name, in order, with ties broken by alphabetization
        String nameWithoutDashes = encryptedName.replace("-", "");
        List<String> charList = AocStringUtils.extractCharacterList(nameWithoutDashes);
        Map<String, Long> charsWithCount = charList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        String computedChecksum = charsWithCount.entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed().thenComparing(Map.Entry<String, Long>::getKey))
                .limit(5)
                .map(Map.Entry<String, Long>::getKey)
                .collect(Collectors.joining());
        return computedChecksum;
    }

    public String decryptName() {
        return AocStringUtils.extractCharacterList(encryptedName).stream()
                .map(this::decryptCharacter)
                .collect(Collectors.joining());
    }

    public String decryptCharacter(String inputChar) {
        if ("-".equals(inputChar)) {
            return " ";
        }
        char c = inputChar.charAt(0);

        return String.valueOf(ShiftCypher.decrypt(c, sectorId));
    }

    public static Room fromInput(String line) {
        Pattern pattern = Pattern.compile("^([^\\d]*)-(\\d+)\\[([a-z]{5})\\]$");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid room input: " + line);
        }
        String encryptedName = matcher.group(1);
        int sectorId = Integer.parseInt(matcher.group(2));
        String checksum = matcher.group(3);
        return new Room(encryptedName, sectorId, checksum);
    }
}
