package be.rouget.puzzles.adventofcode.year2021.day16;

public interface Packet {
    PacketHeader getHeader();
    long computeSumOfVersions();
    long computeValue();
}
