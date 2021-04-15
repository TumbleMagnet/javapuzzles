package be.rouget.puzzles.adventofcode.year2016.day3;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SquaresWithThreeSides {

    private static final String YEAR = "2016";
    private static final String DAY = "03";

    private static final Logger LOG = LogManager.getLogger(SquaresWithThreeSides.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        SquaresWithThreeSides aoc = new SquaresWithThreeSides(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public SquaresWithThreeSides(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return input.stream()
                .map(Triangle::fromInput)
                .filter(Triangle::isValid)
                .count();
    }

    public long computeResultForPart2() {

        List<Triangle> rows = input.stream()
                .map(Triangle::fromInput)
                .collect(Collectors.toList());
        int validTriangles = 0;
        for (int i = 0; i < rows.size()/3; i++) {
            Triangle row1 = rows.get(i*3 + 0);
            Triangle row2 = rows.get(i*3 + 1);
            Triangle row3 = rows.get(i*3 + 2);
            if (new Triangle(row1.getLength1(), row2.getLength1(), row3.getLength1()).isValid()) {
                validTriangles++;
            }
            if (new Triangle(row1.getLength2(), row2.getLength2(), row3.getLength2()).isValid()) {
                validTriangles++;
            }
            if (new Triangle(row1.getLength3(), row2.getLength3(), row3.getLength3()).isValid()) {
                validTriangles++;
            }
        }
        return validTriangles;
    }
}