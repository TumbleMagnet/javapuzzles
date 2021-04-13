package be.rouget.puzzles.adventofcode.year2015.day25;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LetItSnow {

    private static final Logger LOG = LogManager.getLogger(LetItSnow.class);

    // To continue, please consult the code grid in the manual.  Enter the code at row 2981, column 3075.
    private static final int TARGET_ROW = 2981;
    private static final int TARGET_COLUMN = 3075;

    public static void main(String[] args) {
        LetItSnow aoc = new LetItSnow();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
    }

    public long computeResultForPart1() {

        // Codes are filled like this:
        // line 1: 1,1
        // line 2: 2,1 1,2
        // line 3: 3,1 2,2 1,3
        // line 4: 4,1 3,2 2,3 1,4
        // line 5: 5,1 4,2 3,3 2,4 1,5
        int count = 0;
        long code = 20151125;
        for (int line = 1; line < TARGET_ROW+TARGET_COLUMN; line++) {
            for (int x = line; x >= 1; x--) {
                int y = line+1-x;
                count++;
                if (count > 1) {
                    code = nextCode(code);
                }
                if (x == TARGET_ROW && y == TARGET_COLUMN) {
                    LOG.info("Found target location (count: {})", count);
                    return code;
                }
            }
        }
        return -1;
    }

    public static long nextCode(long code) {
        return (code * 252533) % 33554393;
    }
}