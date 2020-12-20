package be.rouget.puzzles.adventofcode.year2020.day5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardingPass {

    private static Logger LOG = LogManager.getLogger(BinaryBoarding.class);

    private int row;
    private int column;

    public BoardingPass(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public BoardingPass(String code) {
        // Split code into row and column parts
        String rowCode = code.substring(0, 7).replace("F", "0").replace("B", "1");
        String columnCode = code.substring(7).replace("L", "0").replace("R", "1");
        row = Integer.parseInt(rowCode, 2);
        column = Integer.parseInt(columnCode, 2);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSeatId() {
        return row * 8 + column;
    }

    @Override
    public String toString() {
        return "BoardingPass{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
