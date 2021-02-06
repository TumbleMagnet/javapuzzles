package be.rouget.puzzles.adventofcode.year2020.day25;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ComboBreaker {

    private static final String YEAR = "2020";
    private static final String DAY = "25";

    private static final Logger LOG = LogManager.getLogger(ComboBreaker.class);

    private final List<String> input;

    public ComboBreaker(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ComboBreaker aoc = new ComboBreaker(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        long cardPublicKey = Long.parseLong(input.get(0));
        long doorPublicKey = Long.parseLong(input.get(1));

        int cardLoopNumber = guessLoopNumber(cardPublicKey);
        LOG.info("Card loop number: {}", cardLoopNumber);

        int doorLoopNumber = guessLoopNumber(doorPublicKey);
        LOG.info("Door loop number: {}", doorLoopNumber);

        return transformSubjectNumber(cardPublicKey, doorLoopNumber);
    }
    public long computeResultForPart2() {
        return -1;
    }

    private int guessLoopNumber(long publicKey) {
        long value = 1;
        int loopCount = 0;
        while (value != publicKey) {
            loopCount++;
            value = transformStep(value, 7);
        }
        return loopCount;
    }

    public static long transformSubjectNumber(long subjectNumber, int numberOfLoops) {
        long value = 1;
        for (int i = 0; i < numberOfLoops; i++) {
            value = transformStep(value, subjectNumber);
        }
        return value;
    }

    private static long transformStep(long value, long subjectNumber) {
        value = value * subjectNumber;
        value = value % 20201227;
        return value;
    }
}