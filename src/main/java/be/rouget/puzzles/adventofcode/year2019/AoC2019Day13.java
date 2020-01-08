package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019Day13 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day13.class);

    public static void main(String[] args) {
        String input = ResourceUtils.readIntoString("aoc_2019_day13_input.txt");
        LOG.info("Result is " + computeResult(input));
    }

    public static long computeResult(String input) {

        Arcade arcade = new Arcade(input);
        arcade.run();
        return arcade.getScore();
    }

    public static class Arcade {

        private static final int MAX_X = 45;
        private static final int MAX_Y = 24;

        private Computer computer;
        private int score = 0;
        private Tile[][] screen = new Tile[MAX_X][MAX_Y];

        // Track horizontal position of ball and paddle
        private int xBall = -1;
        private int xPaddle = -1;

        public Arcade(String program) {
            this.computer = new Computer(program);
            // Init coins
            computer.setMemoryAtPosition(0, 2);
        }

        public void printScreen() {

            for (int y = 0; y < MAX_Y; y++) {
                String line = "";
                for (int x=0; x<MAX_X; x++) {
                    line += screen[x][y].getDrawnAs();
                }
                System.out.println(line);
            }
            System.out.println("Score: " + score);
        }

        public void run() {

            ComputerState state = computer.run();
            int stepCount = 1;
            while (true) {

                // Process output
                List<Integer> output = computer.getOutputAsIntegers();
                Lists.partition(output, 3).stream().forEach(c -> updateFromOutput(c));
                int blockCount = countBlock();

                // Redraw
//                printScreen();
                System.out.println("Steps: " + stepCount +  " - blocks: " + blockCount + " - Score: " + score);

                // Read input or stop
                if (blockCount == 0) {
                    return;
                }
                else if (state == ComputerState.HALTED) {
                    return;
                }
                else {
                    // Read input
                    int joystickInput = computeJoystickPosition();
                    computer.run(joystickInput);
                    stepCount++;
                }
            }
        }

        private int countBlock() {
            int blockCount = 0;
            for (int y = 0; y < MAX_Y; y++) {
                for (int x=0; x<MAX_X; x++) {
                    if (screen[x][y] == Tile.BLOCK) {
                        blockCount++;
                    }
                }
            }
            return blockCount;
        }

        private int computeJoystickPosition() {
            int joystickInput = 0;
            if (xBall > xPaddle) {
                joystickInput = 1;
            }
            if (xBall < xPaddle) {
                joystickInput = -1;
            }
            return joystickInput;
        }

        private void updateFromOutput(List<Integer> tokens) {
            if (tokens.size() != 3) {
                throw new IllegalArgumentException("Expected 3 tokens");
            }
            if (tokens.get(0) == -1) {
                this.score = tokens.get(2);
            }
            else {
                DrawCommand c = new DrawCommand(tokens);
                Tile tileToDraw = c.getTile();
                if (tileToDraw == Tile.BALL) {
                    xBall = c.getX();
                }
                else if (tileToDraw == Tile.PADDLE) {
                    xPaddle = c.getX();
                }
                screen[c.getX()][c.getY()]= tileToDraw;
            }
        }

        public long getScore() {
            return score;
        }
    }

    public static class DrawCommand {
        private Tile tile;
        private int x;
        private int y;

        public DrawCommand(List<Integer> tokens) {
            x = tokens.get(0);
            y = tokens.get(1);
            tile = Tile.fromIntCode(tokens.get(2));
        }

        public Tile getTile() {
            return tile;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "OutputCommand{" +
                    "tile=" + tile +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public enum Tile {
        EMPTY( 0, ' '),
        WALL(  1, '\u2588'),
        BLOCK( 2, '#'),
        PADDLE(3, '_'),
        BALL(  4, 'o'),
        ;

        private int intCode;
        private char drawnAs;

        Tile(int intCode, char drawnAs) {
            this.intCode = intCode;
            this.drawnAs = drawnAs;
        }

        public int getIntCode() {
            return intCode;
        }

        public char getDrawnAs() {
            return drawnAs;
        }

        public static Tile fromIntCode(int code) {
            for (Tile t : Tile.values()) {
                if (t.getIntCode() == code) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Invalid tile code " + code);
        }
    }
}