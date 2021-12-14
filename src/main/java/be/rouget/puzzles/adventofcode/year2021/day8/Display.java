package be.rouget.puzzles.adventofcode.year2021.day8;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.PermutationGenerator;
import com.google.common.collect.Maps;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
public class Display {
    List<String> patterns;
    List<String> outputDigits;

    public static Display fromInput(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input: " + line);
        }
        List<String> patterns = Arrays.stream(parts[0].split(" ")).collect(Collectors.toList());
        List<String> outputDigits = Arrays.stream(parts[1].split(" ")).collect(Collectors.toList());
        return new Display(patterns, outputDigits);
    }

    public String getDecodedOutput() {

        // Find permutation which produces valid digits
        Mapping mapping = new PermutationGenerator<>(SEGMENTS).generatePermutations().stream()
                .map(Mapping::new)
                .filter(this::isValidMapping)
                .findFirst()
                .orElseThrow();

        // Decode the output digits
        return getOutputDigits().stream()
                .map(mapping::decode)
                .map(s -> Digit.fromSegments(s).getCharacter())
                .collect(Collectors.joining());
    }

    private boolean isValidMapping(Mapping m) {
        return getPatterns().stream().map(m::decode).allMatch(this::isDigit);
    }

    private boolean isDigit(String input) {
        return Arrays.stream(Digit.values())
                .anyMatch(d -> d.getSegments().equals(input));
    }

    public static class Mapping {
        Map<String, String> segmentMapping = Maps.newHashMap();

        public Mapping(List<String> permutedSegments) {
            for (int i = 0; i < SEGMENTS.size(); i++) {
                segmentMapping.put(SEGMENTS.get(i), permutedSegments.get(i));
            }
        }

        public String decode(String input) {
            return AocStringUtils.extractCharacterList(input).stream()
                    .map(s -> segmentMapping.get(s))
                    .sorted()
                    .collect(Collectors.joining());
        }

    }

    private static final List<String> SEGMENTS = List.of("a", "b", "c", "d", "e", "f", "g");
}
