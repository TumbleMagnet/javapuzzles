package be.rouget.puzzles.adventofcode.year2022.day06;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;


public class TuningTrouble {

    private static final Logger LOG = LogManager.getLogger(TuningTrouble.class);
    private final String dataStreamBuffer;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(TuningTrouble.class);
        TuningTrouble aoc = new TuningTrouble(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public TuningTrouble(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        dataStreamBuffer = input.get(0);
    }

    public long computeResultForPart1() {
        return findStartOfPacketMarker(dataStreamBuffer, 4);
    }
    
    public long computeResultForPart2() {
        return findStartOfPacketMarker(dataStreamBuffer, 14);
    }

    public static int findStartOfPacketMarker(String input, int sizeOfMarker) {
        for (int i = sizeOfMarker-1; i < input.length(); i++) {
            Set<Character> uniqueChars = Sets.newHashSet();
            for (int j = 0; j < sizeOfMarker; j++) {
                uniqueChars.add(input.charAt(i - j));
            }
            if (uniqueChars.size() == sizeOfMarker) {
                return i+1; // Numbers of characters read in input
            }
        }
        throw new IllegalStateException("Did not find packet marker");
    }
}