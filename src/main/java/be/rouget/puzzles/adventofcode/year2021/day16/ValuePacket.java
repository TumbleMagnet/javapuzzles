package be.rouget.puzzles.adventofcode.year2021.day16;

import lombok.Value;

@Value
public class ValuePacket implements Packet {
    PacketHeader header;
    long value;

    @Override
    public long computeSumOfVersions() {
        return header.getVersion();
    }

    @Override
    public long computeValue() {
        return value;
    }
}
