package be.rouget.puzzles.adventofcode.year2020.day15;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RambunctiousRecitation {

    private static final String YEAR = "2020";
    private static final String DAY = "15";

    private static final Logger LOG = LogManager.getLogger(RambunctiousRecitation.class);

    private final List<String> input;

    public RambunctiousRecitation(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        RambunctiousRecitation aoc = new RambunctiousRecitation(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return computeValueAtTurn(2020L);
    }

    public long computeResultForPart2() {
        return computeValueAtTurn(30000000L);
    }

    private Long computeValueAtTurn(long targetTurn) {
        List<Integer> startingNumbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        MemoryGame game = new MemoryGame(startingNumbers);
        while (game.getTurn() < targetTurn) {
            game.generateNextNumber();
        }
        return game.getLastNumber();
    }
}