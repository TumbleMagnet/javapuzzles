package be.rouget.puzzles.adventofcode.year2021.day16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2021.day16.PacketDecoder.hexToBinary;
import static org.assertj.core.api.Assertions.assertThat;

class PacketDecoderTest {

    @Test
    void testHexToBinary() {
        assertThat(hexToBinary("D2FE28")).isEqualTo("110100101111111000101000");
        assertThat(hexToBinary("38006F45291200")).isEqualTo("00111000000000000110111101000101001010010001001000000000");
        assertThat(hexToBinary("EE00D40C823060")).isEqualTo("11101110000000001101010000001100100000100011000001100000");
    }

    @Test
    void testValuePacket() {
        assertThat(PacketDecoder.parsePacketsFromHexString("D2FE28")).containsExactly(new ValuePacket(new PacketHeader(6, PacketType.VALUE), 2021L));
    }

    @Test
    void testOperatorPacketFixedLength() {
        assertThat(PacketDecoder.parsePacketsFromHexString("38006F45291200")).containsExactly(
                new OperatorPacket(new PacketHeader(1, PacketType.LESS_THAN), List.of(
                        new ValuePacket(new PacketHeader(6, PacketType.VALUE), 10L),
                        new ValuePacket(new PacketHeader(2, PacketType.VALUE), 20L)
                )));
    }

    @Test
    void testOperatorPacketVariableLength() {
        assertThat(PacketDecoder.parsePacketsFromHexString("EE00D40C823060")).containsExactly(
                new OperatorPacket(new PacketHeader(7, PacketType.MAXIMUM), List.of(
                        new ValuePacket(new PacketHeader(2, PacketType.VALUE), 1L),
                        new ValuePacket(new PacketHeader(4, PacketType.VALUE), 2L),
                        new ValuePacket(new PacketHeader(1, PacketType.VALUE), 3L)
                )));
    }

    @Test
    void testOperators() {
        assertThat(computeValue("C200B40A82")).isEqualTo(3L);
        assertThat(computeValue("04005AC33890")).isEqualTo(54L);
        assertThat(computeValue("880086C3E88112")).isEqualTo(7L);
        assertThat(computeValue("CE00C43D881120")).isEqualTo(9L);
        assertThat(computeValue("D8005AC2A8F0")).isEqualTo(1L);
        assertThat(computeValue("F600BC2D8F")).isEqualTo(0L);
        assertThat(computeValue("9C0141080250320F1802104A08")).isEqualTo(1L);
    }

    private static long computeValue(String hexString) {
        return new PacketDecoder(List.of(hexString)).computeResultForPart2();
    }
}