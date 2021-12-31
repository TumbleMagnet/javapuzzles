package be.rouget.puzzles.adventofcode.year2021.day16;

import lombok.Value;

import java.util.List;

@Value
public class OperatorPacket implements Packet {
    PacketHeader header;
    List<Packet> childrenPackets;

    @Override
    public long computeSumOfVersions() {
        return childrenPackets.stream().mapToLong(Packet::computeSumOfVersions).sum() + header.getVersion();
    }

    @Override
    public long computeValue() {
        PacketType type = getHeader().getType();
        if (PacketType.SUM == type) {
            return childrenPackets.stream().mapToLong(Packet::computeValue).sum();
        } else if (PacketType.PRODUCT == type) {
            return childrenPackets.stream().mapToLong(Packet::computeValue).reduce(1L, (x, y) -> x * y);
        } else if (PacketType.MINIMUM == type) {
            return childrenPackets.stream().mapToLong(Packet::computeValue).min().orElseThrow();
        } else if (PacketType.MAXIMUM == type) {
            return childrenPackets.stream().mapToLong(Packet::computeValue).max().orElseThrow();
        } else if (PacketType.GREATER_THAN == type) {
            long value0 = getChildrenPackets().get(0).computeValue();
            long value1 = getChildrenPackets().get(1).computeValue();
            return value0 > value1 ? 1L : 0L;
        } else if (PacketType.LESS_THAN == type) {
            long value0 = getChildrenPackets().get(0).computeValue();
            long value1 = getChildrenPackets().get(1).computeValue();
            return value0 < value1 ? 1L : 0L;
        } else if (PacketType.EQUALS == type) {
            long value0 = getChildrenPackets().get(0).computeValue();
            long value1 = getChildrenPackets().get(1).computeValue();
            return value0 == value1 ? 1L : 0L;
        }
        throw new IllegalStateException("Unsupported operator type: " + type);
    }
}
