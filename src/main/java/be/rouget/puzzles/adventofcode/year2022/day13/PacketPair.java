package be.rouget.puzzles.adventofcode.year2022.day13;

import java.util.List;

public record PacketPair(int index, String packet1, String packet2) {

    public static PacketPair build(int index, List<String> input) {
        if (input.size() != 2) {
            throw new IllegalArgumentException("Expected 2 lines, got " + input.size());
        }
        return new PacketPair(index, input.get(0), input.get(1));
    }

    public boolean isInRightOrder() {
        return new PacketComparator().compare(packet1, packet2) < 0;
    }

}
