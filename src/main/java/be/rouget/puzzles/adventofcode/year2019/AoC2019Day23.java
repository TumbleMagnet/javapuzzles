package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AoC2019Day23 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day23.class);

    private String input;

    public AoC2019Day23(String input) {
        this.input = input;
    }

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day23_input.txt");
        AoC2019Day23 aoc = new AoC2019Day23(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        try {
            Network network = new Network(input, 50);
            network.operate();
        } catch (SpecialPacketException e) {
            LOG.info("Received expected packet: " + e.getPacket());
            return e.getPacket().getY();
        }
        return -1;
    }

    public int computeResultForPart2() {
        return -1;
    }

    public static class NetworkComputer {

        private int networkAddress;
        private Computer computer;
        private Network network;
        protected Queue<Packet> inputQueue = new LinkedList<>();

        public NetworkComputer(String program, int networkAddress, Network network) {
            this.networkAddress = networkAddress;
            this.computer = new Computer(program);
            this.network = network;

            // Boot the computer with the network address
            ComputerState state = computer.run(networkAddress);
        }

        public void receivePacket(Packet packet) {
            inputQueue.add(packet);
        }

        public boolean processInput() {
            long[] input = extractInput();
            ComputerState state = computer.run(input);
            List<String> output = computer.getOutput();
            sendOutput(output);
            return isIdle(input, output);
        }

        private boolean isIdle(long[] input, List<String> output) {
            return (input.length == 1 && input[0] == -1L) && output.isEmpty();
        }

        private void sendOutput(List<String> output) {
            Lists.partition(output, 3).stream().map(v -> toPacket(v)).forEach(p -> network.sendPacket(p));
        }

        private Packet toPacket(List<String> values) {
            if (values.size() != 3) {
                throw new IllegalArgumentException("Could not convert values into a packet: " + values);
            }
            long[] longs = values.stream().mapToLong(Long::parseLong).toArray();
            return new Packet(longs[0], longs[1], longs[2]);
        }

        private long[] extractInput() {
            if (inputQueue.isEmpty()) {
                // No input to consume, return "-1"
                return new long[]{-1L};
            }

            List<Long> inputData = Lists.newArrayList();
            while (!inputQueue.isEmpty()) {
                Packet packet = inputQueue.remove();
                inputData.add(Long.valueOf(packet.getX()));
                inputData.add(Long.valueOf(packet.getY()));
            }
            return inputData.stream().mapToLong(Long::longValue).toArray();
        }
    }

    public static class NotAlwaysTransmitting implements NetworkReceiver {

        private Packet lastPacket;
        private Network network;
        private Packet lastSentPacket;

        public NotAlwaysTransmitting(Network network) {
            this.network = network;
        }

        @Override
        public void receivePacket(Packet packet) {
            this.lastPacket = packet;
        }

        public void trigger() {
            LOG.info("Nat will reactivate traffic...");
            Packet packetToSend = new Packet(0L, lastPacket.getX(), lastPacket.getY());
            if ((lastSentPacket != null) && (lastSentPacket.getY() == packetToSend.getY())) {
                throw new SpecialPacketException(packetToSend);
            }
            network.sendPacket(packetToSend);
            lastSentPacket = packetToSend;
        }
    }

    public static class Network {
        private NetworkComputer[] computers;
        private NotAlwaysTransmitting nat;

        public Network(String program, int numberOfComputers) {
            computers = new NetworkComputer[numberOfComputers];
            for (int i = 0; i < numberOfComputers; i++) {
                computers[i] = new NetworkComputer(program, i, this);
            }
            nat = new NotAlwaysTransmitting(this);
        }

        public void sendPacket(Packet packet) {
            LOG.info("Sending packet " + packet);
            int netWorkAddress = Math.toIntExact(packet.getNetworkAddress());
            if (netWorkAddress == 255) {
                nat.receivePacket(packet);
            }
            else {
                computers[netWorkAddress].receivePacket(packet);
            }
        }

        public void operate() {
            while (true) {
                boolean isIdle = true;
                for (int i = 0; i < computers.length; i++) {
                    boolean isComputerIdle = computers[i].processInput();
                    isIdle = isIdle && isComputerIdle;
                }
                if (isIdle) {
                    nat.trigger();
                }
            }
        }
    }

    public static class Packet {
        long networkAddress;
        long x;
        long y;

        public Packet(long networkAddress, long x, long y) {
            this.networkAddress = networkAddress;
            this.x = x;
            this.y = y;
        }

        public long getNetworkAddress() {
            return networkAddress;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Packet{" +
                    "networkAddress=" + networkAddress +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static class SpecialPacketException extends RuntimeException {
        private Packet packet;

        public SpecialPacketException(Packet packet) {
            this.packet = packet;
        }

        public Packet getPacket() {
            return packet;
        }
    }

    public static interface NetworkReceiver {
        void receivePacket(Packet packet);
    }
}