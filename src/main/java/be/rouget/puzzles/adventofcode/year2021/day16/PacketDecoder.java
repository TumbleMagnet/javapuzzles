package be.rouget.puzzles.adventofcode.year2021.day16;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PacketDecoder {

    private static final Logger LOG = LogManager.getLogger(PacketDecoder.class);
    public static final int HEADER_SIZE = 6;
    public static final int LITERAL_BLOCK_SIZE = 5;
    private final String binaryInput;
    private String remainingInput;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(PacketDecoder.class);
        PacketDecoder aoc = new PacketDecoder(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public static List<Packet> parsePacketsFromHexString(String hexInput) {
        return new PacketDecoder(List.of(hexInput)).parse();
    }

    public static List<Packet> parsePacketsFromBinaryString(String binaryInput) {
        return new PacketDecoder(binaryInput).parse();
    }

    public PacketDecoder(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        binaryInput = hexToBinary(input.get(0));
    }

    public PacketDecoder(String binaryInput) {
        this.binaryInput = binaryInput;
    }

    public List<Packet> parse() {
        remainingInput = binaryInput;
        List<Packet> packets = Lists.newArrayList();
        while (!isEmpty(remainingInput)) {
            packets.add(parseNextPacket());
        }
        return packets;
    }

    private Packet parseNextPacket() {
        PacketHeader header = PacketHeader.parse(readBits(HEADER_SIZE));
        if (header.getType() == PacketType.VALUE) {
            long value = readLiteralValue();
            return new ValuePacket(header, value);
        } else {
            return parseBodyOfOperatorPacket(header);
        }
    }

    private Packet parseBodyOfOperatorPacket(PacketHeader header) {
        String lengthTypeID = readBits(1);
        if ("0".equals(lengthTypeID)) {
            String lengthAsBinary = readBits(15);
            int bodyLength = Integer.parseInt(lengthAsBinary, 2);
            String body = readBits(bodyLength);
            List<Packet> childrenPackets = parsePacketsFromBinaryString(body);
            return new OperatorPacket(header, childrenPackets);
        }
        else {
            String numberOfPacketsAsBinary = readBits(11);
            int numberOfPackets = Integer.parseInt(numberOfPacketsAsBinary, 2);
            List<Packet> childrenPackets = Lists.newArrayList();
            for (int i = 0; i < numberOfPackets; i++) {
                childrenPackets.add(parseNextPacket());
            }
            return new OperatorPacket(header, childrenPackets);
        }
    }

    private long readLiteralValue() {
        StringBuilder literalBinary = new StringBuilder();
        while (true) {
            String part = readBits(LITERAL_BLOCK_SIZE);
            String indicatorBit = StringUtils.left(part, 1);
            String partBinary = StringUtils.right(part, 4);
            literalBinary.append(partBinary);
            if ("0".equals(indicatorBit)) {
                break;
            }
        }
        return Long.parseLong(literalBinary.toString(), 2);
    }

    private String readBits(int numberOfBits) {
        if (remainingInput.length() < numberOfBits) {
            throw new IllegalArgumentException("Cannot read " + numberOfBits + " from \"" + remainingInput + "\"");
        }
        String result = StringUtils.left(remainingInput, numberOfBits);
        remainingInput = StringUtils.right(remainingInput, remainingInput.length() - numberOfBits);
        return result;
    }

    public long computeResultForPart1() {
        return parse().stream().mapToLong(Packet::computeSumOfVersions).sum();
    }

    public long computeResultForPart2() {
        List<Packet> packets = parse();
        return packets.get(0).computeValue();
    }

    private boolean isEmpty(String remainingInput) {
        return StringUtils.isEmpty(remainingInput) || StringUtils.containsOnly(remainingInput, '0');
    }

    public static String hexToBinary(String hexString) {
        String binary = hexString.replaceAll("0", "0000");
        binary = binary.replaceAll("1", "0001");
        binary = binary.replaceAll("2", "0010");
        binary = binary.replaceAll("3", "0011");
        binary = binary.replaceAll("4", "0100");
        binary = binary.replaceAll("5", "0101");
        binary = binary.replaceAll("6", "0110");
        binary = binary.replaceAll("7", "0111");
        binary = binary.replaceAll("8", "1000");
        binary = binary.replaceAll("9", "1001");
        binary = binary.replaceAll("A", "1010");
        binary = binary.replaceAll("B", "1011");
        binary = binary.replaceAll("C", "1100");
        binary = binary.replaceAll("D", "1101");
        binary = binary.replaceAll("E", "1110");
        binary = binary.replaceAll("F", "1111");
        return binary;
    }
}