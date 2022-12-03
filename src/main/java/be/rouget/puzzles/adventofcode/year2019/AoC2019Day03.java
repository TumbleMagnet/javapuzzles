package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AoC2019Day03 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day03.class);

    private List<String> path1;
    private List<String> path2;

    public AoC2019Day03(String input1, String input2) {
        path1 = Lists.newArrayList(input1.split(","));
        path2 = Lists.newArrayList(input2.split(","));
    }

    public int computeDistanceOfTheClosestIntersection() {

        // Compute ans store points for wire 1
        Map<GridPoint, Integer> wire1Points = Maps.newHashMap();
        GridPoint position = new GridPoint(0,0);
        int step = 1;
        for (String nextPath: path1) {
            List<GridPoint> points = position.connectPath(new Path(nextPath));
            for (GridPoint newPoint: points) {
                wire1Points.put(newPoint, Integer.valueOf(step));
                position = newPoint;
                step++;
            }
        }

        // Follow wire 2 and find distance of best intersection
        Integer bestDistance = null;
        position = new GridPoint(0,0);
        step = 1;
        for (String nextPath: path2) {
            List<GridPoint> points = position.connectPath(new Path(nextPath));
            for (GridPoint newPoint: points) {
                if (wire1Points.containsKey(newPoint)) {
                    Integer wire1Steps = wire1Points.get(newPoint);
                    if ((bestDistance == null)
                        || (wire1Steps + step < bestDistance)) {
                        bestDistance = wire1Steps + step;
                    }
                }
                position = newPoint;
                step++;
            }
        }
        return bestDistance;
    }


    public static void main(String[] args) {
        LOG.info("Starting...");
        List<String> inputLines = ResourceUtils.readLines("aoc_2019_day03_input.txt");
        if (inputLines.size() != 2) {
            throw new IllegalArgumentException("Input contain more than 2 lines");
        }
        AoC2019Day03 day03 = new AoC2019Day03(inputLines.get(0), inputLines.get(1));
        LOG.info("Distance: " + day03.computeDistanceOfTheClosestIntersection());
    }

    public static class GridPoint {
        private int x;
        private int y;

        public GridPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public List<GridPoint> connectPath(Path path) {

            int newX = x;
            int newY = y;
            int deltaX = 0;
            int deltaY = 0;

            switch (path.getDirection()) {
                case R: deltaX = 1; break;
                case L: deltaX = -1; break;
                case U: deltaY = 1; break;
                case D: deltaY = -1; break;
                default:
                    throw new IllegalArgumentException("Unknown direction " + path.getDirection());
            }

            List<GridPoint> newPoints = Lists.newArrayList();
            for (int i = 0; i < path.length; i++) {
                newX += deltaX;
                newY += deltaY;
                newPoints.add(new GridPoint(newX, newY));
            }
            return newPoints;
        }

        public int distanceFromStart() {
            return Math.abs(x) + Math.abs(y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GridPoint gridPoint = (GridPoint) o;
            return x == gridPoint.x &&
                    y == gridPoint.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }
    }

    public static enum Direction {
        L, R, U, D;
    }

    public static class Path {
        private Direction direction;
        private int length;

        public Path(String pathString) {
            direction = Direction.valueOf(pathString.substring(0,1));
            length = Integer.valueOf(pathString.substring(1));
        }

        public Direction getDirection() {
            return direction;
        }

        public int getLength() {
            return length;
        }
    }
}