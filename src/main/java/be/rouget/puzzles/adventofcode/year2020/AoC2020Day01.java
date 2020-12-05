package be.rouget.puzzles.adventofcode.year2020;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2020Day01 {

    private static Logger LOG = LogManager.getLogger(AoC2020Day01.class);

    private List<String> input;

    public AoC2020Day01(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2020_day01_input.txt");
        LOG.info("Input has {} lines", input.size());
        AoC2020Day01 aoc = new AoC2020Day01(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        // Convert input into an array of longs
        Long[] values = input.stream().map(Long::parseLong).toArray(Long[]::new);

        // Find pair whose sum equals 2020
        long count = 0;
        for (int i = 0; i < values.length-1; i++) {
            for (int j = i+1; j < values.length; j++) {
                Pair candidate = new Pair(values[i], values[j]);
                count++;
//                LOG.info("Trying pair {}...", candidate);
                if (candidate.getSum().equals(2020L)) {
                    LOG.info("Found pair after {} tries: {}", count, candidate);
                    return candidate.getProduct();
                }
            }
        }
        throw new IllegalStateException("Found no matching pair");
    }
    public long computeResultForPart2() {

        // Convert input into an array of longs
        Long[] values = input.stream().map(Long::parseLong).toArray(Long[]::new);

        // Find pair whose sum equals 2020
        long count = 0;
        for (int i = 0; i < values.length-1; i++) {
            for (int j = i+1; j < values.length; j++) {
                for (int z = j+1; z < values.length; z++) {
                    Triplet candidate = new Triplet(values[i], values[j], values[z]);
                    count++;
//                LOG.info("Trying triplet {}...", candidate);
                    if (candidate.getSum().equals(2020L)) {
                        LOG.info("Found triplet after {} tries: {}", count, candidate);
                        return candidate.getProduct();
                    }
                }
            }
        }
        throw new IllegalStateException("Found no matching pair");
    }

    public static class Pair {

        private Long first;
        private Long second;

        public Pair(Long first, Long second) {
            this.first = first;
            this.second = second;
        }

        public Long getSum() {
            return first + second;
        }

        public Long getProduct() {
            return first * second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    public static class Triplet {
        private Long first;
        private Long second;
        private Long third;

        public Triplet(Long first, Long second, Long third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public Long getSum() {
            return first + second + third;
        }

        public Long getProduct() {
            return first * second * third;
        }

        @Override
        public String toString() {
            return "Triplet{" +
                    "first=" + first +
                    ", second=" + second +
                    ", third=" + third +
                    '}';
        }
    }
}