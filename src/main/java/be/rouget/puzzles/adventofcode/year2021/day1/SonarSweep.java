package be.rouget.puzzles.adventofcode.year2021.day1;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SonarSweep {

    private static final String YEAR = "2021";
    private static final String DAY = "01";

    private static final Logger LOG = LogManager.getLogger(SonarSweep.class);
    private final List<Integer> measures;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SonarSweep aoc = new SonarSweep(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SonarSweep(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        measures = input.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        Integer previousMeasure = null;
        int numberOfIncreases = 0;
        for (Integer measure : measures) {
            if (previousMeasure != null && measure.compareTo(previousMeasure) > 0) {
                numberOfIncreases++;
            }
            previousMeasure = measure;
        }
        return numberOfIncreases;

    }
    public long computeResultForPart2() {
        int numberOfIncreases = 0;
        for (int i = 3; i < measures.size(); i++) {
            int currentMeasure = measures.get(i) + measures.get(i-1) + measures.get(i-2);
            int previousMeasure = measures.get(i-1) + measures.get(i-2) + measures.get(i-3);
            if (currentMeasure > previousMeasure) {
                numberOfIncreases++;
            }
        }
        return numberOfIncreases;
    }
}