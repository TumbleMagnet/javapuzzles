package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019Day19 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day19.class);

    private String droneProgram;
    
    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day19_input.txt");
        AoC2019Day19 aoc = new AoC2019Day19(input);
        LOG.info("Result of part1 is " + aoc.computeSizeOfBeamInSquare(50));
        LOG.info("Result of part2 is " + aoc.computePositionOfClosestSquareFittingInBeam(100));
    }

    public AoC2019Day19(String input) {
        this.droneProgram = input;
    }

    public int computePositionOfClosestSquareFittingInBeam(int sizeOfSquare) {

        for (int x = 1550; x < Integer.MAX_VALUE; x++) {
            boolean foundBeam = false;
            int firstYInBeam = 0;
            if (x % 10 == 0) {
                LOG.info("x=" + x);
            }
            for (int y = firstYInBeam; y < Integer.MAX_VALUE; y++) {
                boolean inBeam = isPositionInBeam(x, y);
                if (inBeam) {
                    if (!foundBeam) {
                        foundBeam = true;
                        firstYInBeam = y;
                    }
                    if (squareFits(x, y, sizeOfSquare)) {
                        LOG.info("Square fits at " + x + ", " + y + ": d2=" + (x*x+y*y));
                        return x * 10000 + y;
                    }
                }
                else {
                    if (foundBeam) {
                        break;
                    }
                }
            }
        }
        throw new IllegalStateException("Did not find fitting position in Integer range");
    }

    private boolean squareFits(int x, int y, int sizeOfSquare) {
        return // isPositionInBeam(x, y) &&
                isPositionInBeam(x + 99, y)
                && isPositionInBeam(x, y + 99);
    }

    public int computeSizeOfBeamInSquare(int sizeOfSquare) {
        int sizeOfBeam = 0;
        for (int x = 0; x < sizeOfSquare; x++) {
            String line = "";
            for (int y = 0; y < sizeOfSquare; y++) {
                if (isPositionInBeam(x, y)) {
                    sizeOfBeam++;
                    line += "#";
                }
                else {
                    line += ".";
                }
            }
            System.out.println(line);
        }
        return sizeOfBeam;
    }

    public boolean isPositionInBeam(int x, int y) {
        Computer computer = new Computer(droneProgram);
        computer.run(x, y);
        List<String> output =  computer.getOutput();
        if (output.size() != 1) {
            throw new IllegalStateException("Unexpected output " + output);
        }
        int tractorMeasure = Integer.parseInt(output.get(0));
        if (tractorMeasure != 0 && tractorMeasure != 1) {
            throw new IllegalStateException("Unexpected output " + output);
        }
        return tractorMeasure == 1;
    }
}