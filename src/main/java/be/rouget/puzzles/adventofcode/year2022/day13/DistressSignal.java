package be.rouget.puzzles.adventofcode.year2022.day13;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.IntStream;


public class DistressSignal {

    private static final Logger LOG = LogManager.getLogger(DistressSignal.class);
    
    private final List<String> input;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(DistressSignal.class);
        DistressSignal aoc = new DistressSignal(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public DistressSignal(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {

        // Parse input into packet pairs
        List<String> inputWithoutBlankLines = input.stream()
                .filter(StringUtils::isNotBlank)
                .toList();
        List<List<String>> inputPairs = ListUtils.partition(inputWithoutBlankLines, 2);
        List<PacketPair> packetPairs = IntStream.rangeClosed(1, inputPairs.size())
                .mapToObj(index -> PacketPair.build(index, inputPairs.get(index - 1)))
                .toList();
        LOG.info("Found {} packet pairs...", packetPairs.size());
        
        // Sum indexes of pairs in correct order
        return packetPairs.stream()
                .filter(PacketPair::isInRightOrder)
                .mapToInt(PacketPair::index)
                .sum();
    }
    
    public long computeResultForPart2() {

        // Build augmented lists of packets
        List<String> inputWithoutBlankLines = input.stream()
                .filter(StringUtils::isNotBlank)
                .toList();
        
        String divider1 = "[[2]]";
        String divider2 = "[[6]]";
        
        List<String> augmentedInput = Lists.newArrayList(inputWithoutBlankLines);
        augmentedInput.add(divider1);
        augmentedInput.add(divider2);
        
        // Sort list of packets
        List<String> sortedPackets = augmentedInput.stream()
                .sorted(new PacketComparator())
                .toList();
        
        // Compute decoder key
        long decoderKey = 1;
        for (int i = 0; i < sortedPackets.size(); i++) {
            String packet = sortedPackets.get(i);
            if (divider1.equals(packet) || divider2.equals(packet)) {
                decoderKey = decoderKey * (i + 1);
            }
        }
        return decoderKey;
    }
}