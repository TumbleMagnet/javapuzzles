package be.rouget.puzzles.adventofcode.year2023.day09;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;


public class MirageMaintenance {

    private static final Logger LOG = LogManager.getLogger(MirageMaintenance.class);
    private final List<List<Long>> histories;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(MirageMaintenance.class);
        MirageMaintenance aoc = new MirageMaintenance(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public MirageMaintenance(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        histories = input.stream()
                .map(MirageMaintenance::parseHistory)
                .toList();
    }

    public long computeResultForPart1() {
        return histories.stream()
                .mapToLong(this::extrapolateNextValue)
                .sum();
    }
    
    private long extrapolateNextValue(List<Long> history) {
        
        // If history is all zeros, then next value is zero
        if (history.stream().allMatch(l -> l.equals(0L))) {
            return 0L;
        }
        
        // Else extrapolate next value of the series of differences
        List<Long> seriesOfDifferences = toSeriesOfDifferences(history);
        long nextValueOfDifferences = extrapolateNextValue(seriesOfDifferences);
        
        // Next value if last value + next value of differences
        return history.getLast() + nextValueOfDifferences;
    }

    private List<Long> toSeriesOfDifferences(List<Long> history) {
        List<Long> differences = Lists.newArrayList();
        for (int i = 0; i < history.size() - 1; i++) {
            long value1 = history.get(i);
            long value2 = history.get(i+1);
            long difference = value2 - value1;
            differences.add(difference);
        }
        return differences;
    }

    public long computeResultForPart2() {
        return histories.stream()
                .map(Lists::reverse)
                .mapToLong(this::extrapolateNextValue)
                .sum();
    }

    @VisibleForTesting
    protected static List<Long> parseHistory(String line) {
        // 4 3 0 -9 -27 -42 5 273 1100 3132 7576 16746 35225 72193 145785 290758 571276 1101281 2075720 3816857 6841029
        String[] tokens = line.split(" ");
        return Arrays.stream(tokens)
                .map(Long::parseLong)
                .toList();
    }
}