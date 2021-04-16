package be.rouget.puzzles.adventofcode.year2016.day6;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SignalsAndNoise {

    private static final String YEAR = "2016";
    private static final String DAY = "06";

    private static final Logger LOG = LogManager.getLogger(SignalsAndNoise.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SignalsAndNoise aoc = new SignalsAndNoise(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SignalsAndNoise(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public String computeResultForPart1() {
        ErrorCorrector errorCorrector = createAndFeedErrorCorrector();
        return errorCorrector.getCorrectedMessageForPart1();
    }

    public String computeResultForPart2() {
        ErrorCorrector errorCorrector = createAndFeedErrorCorrector();
        return errorCorrector.getCorrectedMessageForPart2();
    }

    private ErrorCorrector createAndFeedErrorCorrector() {
        ErrorCorrector errorCorrector = new ErrorCorrector(input.get(0).length());
        for (String message : input) {
            errorCorrector.addMessage(message);
        }
        return errorCorrector;
    }
}