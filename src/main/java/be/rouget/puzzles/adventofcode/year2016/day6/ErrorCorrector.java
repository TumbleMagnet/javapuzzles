package be.rouget.puzzles.adventofcode.year2016.day6;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ErrorCorrector {
    private final int messageSize;
    private final List<Map<String, Long>> frequencies = Lists.newArrayList();

    public ErrorCorrector(int messageSize) {
        this.messageSize = messageSize;
        for (int i = 0; i < this.messageSize; i++) {
            frequencies.add(Maps.newHashMap());
        }
    }

    public void addMessage(String message) {
        if (message.length() != messageSize) {
            throw new IllegalArgumentException("Input message with invalid size: " + message);
        }
        String[] characters = AocStringUtils.splitCharacters(message);
        for (int i = 0; i < characters.length; i++) {
            addCharacter(characters[i], i);
        }
    }

    private void addCharacter(String character, int index) {
        Map<String, Long> frequencyMap = frequencies.get(index);
        Long charCount = frequencyMap.get(character);
        if (charCount == null) {
            charCount = 0L;
        }
        charCount++;
        frequencyMap.put(character, charCount);
    }

    public String getCorrectedMessageForPart1() {
        return getCorrectedMessage(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed());
    }

    public String getCorrectedMessageForPart2() {
        return getCorrectedMessage(Comparator.comparing(Map.Entry<String, Long>::getValue));
    }

    private String getCorrectedMessage(Comparator<Map.Entry<String, Long>> comparator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageSize; i++) {
            Map<String, Long> frequencyMap = frequencies.get(i);
            String bestCharForIndex = frequencyMap.entrySet().stream()
                    .min(comparator)
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            sb.append(bestCharForIndex);
        }
        return sb.toString();
    }

}
