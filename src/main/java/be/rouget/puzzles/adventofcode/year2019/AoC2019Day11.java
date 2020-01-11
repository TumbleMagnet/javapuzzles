package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AoC2019Day11 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day11.class);

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day11_input.txt");
        LOG.info("Result is : " + computeResult(input));
    }

    public static long computeResult(String input) {

        PanelRepository.paintPanelAtPosition(new Position(0,0), Color.WHITE);

        Robot robot = new Robot(input);
        robot.run();

        for (int y = PanelRepository.getMinY(); y <= PanelRepository.getMaxY(); y++) {
            String line = "";
            for (int x = PanelRepository.getMinX(); x <= PanelRepository.getMaxX(); x++) {
                switch (PanelRepository.getColorOfPanelAtPosition(new Position(x, y))) {
                    case BLACK: line += ' '; break;
                    case WHITE: line += '\u2588'; break;
                }
            }
            System.out.println(line);
        }
        return PanelRepository.getPanels().stream().filter(Panel::isPainted).count();
    }

    public static class Robot {
        private Computer computer = null;
        private Position position = new Position(0,0);
        private Direction direction = Direction.UP;

        public Robot(String program) {
            this.computer = new Computer(program);
        }

        public void run() {

            while (true) {
                Color currentColor = PanelRepository.getColorOfPanelAtPosition(position);
                LOG.info("Current position is: " + position);
                LOG.info("Panel color is " + currentColor.name());
                LOG.info("Compute input is " + currentColor.getRobotCode());
                ComputerState computerState = computer.run(currentColor.getRobotCode());

                List<String> outputs = computer.getOutput();
                LOG.info("Compute output is " + outputs);
                if (outputs.size() != 2) {
                    throw new IllegalStateException("Unexpected output: " + outputs);
                }
                Color newColor = Color.fromRobotCode(Integer.parseInt(outputs.get(0)));
                LOG.info("New color to paint " + newColor);
                PanelRepository.paintPanelAtPosition(position, newColor);

                int directionInstruction = Integer.parseInt(outputs.get(1));
                if (directionInstruction == 0) {
                    this.direction = this.direction.turnLeft();
                }
                else {
                    this.direction = this.direction.turnRight();
                }
                LOG.info("New direction is " + this.direction);

                this.position = this.position.newPositionInDirection(this.direction);
                if (computerState == ComputerState.HALTED) {
                    return;
                }
            }
        }
    }
    public static class PanelRepository {

        private static Map<Position, Panel> panels = Maps.newHashMap();
        private static int minX = 0;
        private static int minY = 0;
        private static int maxX = 0;
        private static int maxY = 0;


        public static Color getColorOfPanelAtPosition(Position p ) {
            return getOrCreatePanel(p).getColor();
        }

        private static Panel getOrCreatePanel(Position p) {
            Panel panel = panels.get(p);
            if (panel == null) {
                panel = new Panel(p);
                panels.put(p, panel);
                minX = Math.min(minX, p.getX());
                maxX = Math.max(maxX, p.getX());
                minY = Math.min(minY, p.getY());
                maxY = Math.max(maxY, p.getY());
            }
            return panel;
        }

        public static void paintPanelAtPosition(Position p, Color c) {
            getOrCreatePanel(p).paint(c);
        }

        public static Collection<Panel> getPanels() {
            return panels.values();
        }

        public static int getMinX() {
            return minX;
        }

        public static int getMinY() {
            return minY;
        }

        public static int getMaxX() {
            return maxX;
        }

        public static int getMaxY() {
            return maxY;
        }
    }

    public static class Panel {
        private Position position;
        private Color color = Color.BLACK;
        private boolean isPainted = false;

        public Panel(Position position) {
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }

        public Color getColor() {
            return color;
        }

        public boolean isPainted() {
            return isPainted;
        }

        public void paint(Color color) {
            this.color = color;
            this.isPainted = true;
        }
    }

    public enum Color {
        BLACK(0),
        WHITE(1);

        private int robotCode;

        Color(int robotCode) {
            this.robotCode = robotCode;
        }

        public static Color fromRobotCode(int colorCode) {
            for (Color c : Color.values()) {
                if (colorCode == c.robotCode) {
                    return c;
                }
            }
            throw new IllegalArgumentException("Invalid color code");
        }

        public int getRobotCode() {
            return robotCode;
        }
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position newPositionInDirection(Direction d) {
            switch (d) {
                case UP: return new Position(this.x, this.y-1);
                case LEFT: return new Position(this.x-1, this.y);
                case DOWN: return new Position(this.x, this.y+1);
                case RIGHT: return new Position(this.x+1, this.y);
                default:
                    throw new IllegalStateException("Unknown direction " + d);
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        public Direction turnLeft() {
            switch (this) {
                case UP: return LEFT;
                case LEFT: return DOWN;
                case DOWN: return RIGHT;
                case RIGHT: return UP;
                default:
                    throw new IllegalStateException("Unknown direction " + this.name());
            }
        }

        public Direction turnRight() {
            switch (this) {
                case UP: return RIGHT;
                case LEFT: return UP;
                case DOWN: return LEFT;
                case RIGHT: return DOWN;
                default:
                    throw new IllegalStateException("Unknown direction " + this.name());
            }
        }
    }
}