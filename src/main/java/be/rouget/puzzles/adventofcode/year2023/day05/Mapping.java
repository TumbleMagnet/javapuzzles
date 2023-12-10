package be.rouget.puzzles.adventofcode.year2023.day05;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Mapping(String from, String to, List<MappingLine> lines) {

    public List<Long> mapSourcesToDestinations(List<Long> sources) {
        return sources.stream()
                .map(this::mapValue)
                .toList();
    }

    public List<LongRange> mapRanges(List<LongRange> ranges) {
        return ranges.stream()
                .map(this::mapRange)
                .map(this::mappedRangesToList)
                .flatMap(List::stream)
                .toList();
    }

    private List<LongRange> mappedRangesToList(MappedRanges mappedRanges) {
        List<LongRange> result = Lists.newArrayList();
        result.addAll(mappedRanges.inputRanges());
        result.addAll(mappedRanges.mappedRanges());
        return result;
    }

    public MappedRanges mapRange(LongRange inputRange) {

        List<LongRange> remainingInput = Lists.newArrayList(inputRange);
        List<LongRange> outputRanges = Lists.newArrayList();
        
        for (MappingLine mappingLine : lines) {
            List<LongRange> newRemainingInput = Lists.newArrayList();
            for (LongRange input : remainingInput) {
                MappedRanges mappedRanges = mappingLine.mapRange(input);
                outputRanges.addAll(mappedRanges.mappedRanges());
                newRemainingInput.addAll(mappedRanges.inputRanges());
            }
            remainingInput = newRemainingInput;
        }

        return new MappedRanges(remainingInput, outputRanges);
    }

    public long mapValue(long source) {
        Optional<MappingLine> optionalLine = lines.stream()
                .filter(line -> line.containsSource(source))
                .findFirst();
        if (optionalLine.isPresent()) {
            MappingLine matchingLine = optionalLine.get();
            return matchingLine.mapSourceToDestination(source);
        }
        return source;
    }

    public static Mapping parse(List<String> input) {
        Pattern pattern = Pattern.compile("(.*)-to-(.*) map:");
        Matcher matcher = pattern.matcher(input.getFirst());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String from = matcher.group(1);
        String to = matcher.group(2);
        List<MappingLine> lines = input.subList(1, input.size()).stream()
                .map(MappingLine::parse)
                .toList();
        return new Mapping(from, to, lines);
    }
}
