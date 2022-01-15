package be.rouget.puzzles.adventofcode.year2021.day18;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class Snailfish {

    private static final Logger LOG = LogManager.getLogger(Snailfish.class);
    private final List<Pair> pairs;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(Snailfish.class);
        Snailfish aoc = new Snailfish(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public Snailfish(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        pairs = input.stream()
                .map(Pair::parse)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        Pair result = null;
        for (Pair current : pairs) {
            if (result == null) {
                result = current;
            } else {
                result = result.add(current);
            }
        }
        return result.getMagnitude();
    }

    public long computeResultForPart2() {
        int max = -1;
        for (int i = 0; i < pairs.size(); i++) {
            for (int j = i; j < pairs.size(); j++) {
                Pair pair1 = pairs.get(i);
                Pair pair2 = pairs.get(j);
                max = Math.max(max, pair1.add(pair2).getMagnitude());
                max = Math.max(max, pair2.add(pair1).getMagnitude());
            }
        }
        return max;
    }
}