package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class AoC2019Day15 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day15.class);

    public static void main(String[] args) {
        LOG.info("Starting...");
        String input = ResourceUtils.readIntoString("aoc_2019_day15_input.txt");
        MapExplorer m = new MapExplorer(input);
        m.explore();
        LOG.info("Length of shortest path to oxygen system: " + m.lengthOfShortestPathToOxygenSystem());
        LOG.info("Time to fill the map with oxygen: " + m.timeToFillMapWithOxygen());
    }

    public static class MapExplorer {

        private Computer computer;
        private Map<Position, Item> map = Maps.newHashMap();
        private Position droidPosition = new Position(0, 0);
        private Stack<Position> executedPath = new Stack<>();
        private Position oxygenSystemPosition;

        public MapExplorer(String program) {

            // Start computer
            computer = new Computer(program);

            // Assume that droid starts on an empty position
            map.put(droidPosition, Item.EMPTY);
        }

        public void explore() {
            while (true) {
                List<Direction> possibleMoves = movesWhichDiscoverNewArea();
                if (!possibleMoves.isEmpty()) {
                    executeMove(possibleMoves.get(0));
                }
                else if (!executedPath.isEmpty()) {
                    // Backtrack of one move
                    backtrackOneMove();
                }
                else {
                    // Nothing left to explore
                    LOG.info("Exploration is complete, printing map...");
                    printMap();
                    return;
                }
            }
        }

        private List<Direction> movesWhichDiscoverNewArea() {
            return Arrays.stream(Direction.values())
                    .filter(d -> !map.containsKey(droidPosition.move(d)))
                    .collect(Collectors.toList());
        }

        private void backtrackOneMove() {
            Position target = executedPath.pop();
            Direction d = droidPosition.directionToGetTo(target);
            DroidOutput output = executeMoveCommand(d);
            if ((output != DroidOutput.EMPTY) && (output != DroidOutput.OXYGEN_SYSTEM)) {
                throw new IllegalStateException("Error when backtracking from " + droidPosition + " to " + target + ", got " + output + " for direction " + d);
            }
            droidPosition = target;
        }

        private void executeMove(Direction d) {
            Position targetPosition = droidPosition.move(d);
            DroidOutput output = executeMoveCommand(d);
            if (output == DroidOutput.WALL) {
                // Hit a WALL, no position change
                map.put(targetPosition, Item.WALL);
            }
            else if (output == DroidOutput.EMPTY) {
                // Target is EMPTY, move to target
                map.put(targetPosition, Item.EMPTY);
                executedPath.push(droidPosition);
                droidPosition = targetPosition;
            }
            else if (output == DroidOutput.OXYGEN_SYSTEM) {
                // Target is OXYGEN SYSTEM, move to target
                LOG.info("Found oxygen system at " + targetPosition);
                oxygenSystemPosition = targetPosition;
                map.put(targetPosition, Item.OXYGEN_SYSTEM);
                executedPath.push(droidPosition);
                droidPosition = targetPosition;
            }
        }

        private DroidOutput executeMoveCommand(Direction d) {
            computer.run(d.getCode());
            List<String> output = computer.getOutput();
            if (output.size() != 1) {
                throw new IllegalStateException("Unsupported output " + output);
            }
            return DroidOutput.fromCode(Integer.parseInt(output.get(0)));
        }

        public int lengthOfShortestPathToOxygenSystem() {
            return lengthOfShortestPathTo(oxygenSystemPosition);
        }

        private int lengthOfShortestPathTo(Position target) {

            Queue<VisitedPosition> queue = new LinkedList<>();
            Set<VisitedPosition> visitedNodes = Sets.newHashSet();

            VisitedPosition start = new VisitedPosition(new Position(0,0), 0);
            queue.add(start);
            visitedNodes.add(start);

            while (!queue.isEmpty()) {
                VisitedPosition v = queue.remove();
                if (v.getPosition().equals(target)) {
                    // Found target, stop search and return depth
                    return v.getDepth();
                }
                List<Position> neighbours = accessibleNeighboursAccordingToMap(v.getPosition());
                for (Position neighbour : neighbours) {
                    VisitedPosition newVisitedPosition = new VisitedPosition(neighbour, v.getDepth() + 1);
                    if (!visitedNodes.contains(newVisitedPosition)) {
                        queue.add(newVisitedPosition);
                        visitedNodes.add(newVisitedPosition);
                    }
                }
            }
            throw new IllegalStateException("Could not find path to target...");
        }

        public int timeToFillMapWithOxygen() {
            return timeToFillMapWithOxygenFrom(oxygenSystemPosition);
        }

        private int timeToFillMapWithOxygenFrom(Position source) {

            Queue<VisitedPosition> queue = new LinkedList<>();
            Set<VisitedPosition> visitedNodes = Sets.newHashSet();

            VisitedPosition start = new VisitedPosition(source, 0);
            queue.add(start);
            visitedNodes.add(start);

            while (!queue.isEmpty()) {
                VisitedPosition v = queue.remove();
                List<Position> neighbours = accessibleNeighboursAccordingToMap(v.getPosition());
                for (Position neighbour : neighbours) {
                    VisitedPosition newVisitedPosition = new VisitedPosition(neighbour, v.getDepth() + 1);
                    if (!visitedNodes.contains(newVisitedPosition)) {
                        queue.add(newVisitedPosition);
                        visitedNodes.add(newVisitedPosition);
                    }
                }
            }

            // All nodes are visited, return max depth of visited nodes
            return visitedNodes.stream().max(Comparator.comparing(VisitedPosition::getDepth)).get().getDepth();
        }

        private List<Position> accessibleNeighboursAccordingToMap(Position current) {
            return Arrays.stream(Direction.values())
                    .map(d -> current.move(d))
                    .filter(p -> map.get(p) != Item.WALL)
                    .collect(Collectors.toList());
        }

        private void printMap() {
            int minX = map.keySet().stream().mapToInt(Position::getX).min().orElseThrow(IllegalStateException::new);
            int maxX = map.keySet().stream().mapToInt(Position::getX).max().orElseThrow(IllegalStateException::new);
            int minY = map.keySet().stream().mapToInt(Position::getY).min().orElseThrow(IllegalStateException::new);
            int maxY = map.keySet().stream().mapToInt(Position::getY).max().orElseThrow(IllegalStateException::new);

            Position upperLeft = new Position(minX, minY);
            Position lowerRight = new Position(maxX, maxY);

            for (int y=upperLeft.getY(); y<=lowerRight.getY(); y++) {
                String line = "";
                for (int x=upperLeft.getX(); x<=lowerRight.getX(); x++) {
                    if ((x==0) && (y==0)) {
                        line += 'O'; // Show starting position
                    }
                    else {
                        Item item = map.get(new Position(x, y));
                        if (item != null) {
                            line += item.getCharacter();
                        }
                        else {
                            line += Item.WALL.getCharacter(); // Draw non-accessible square as WALL
                        }
                    }
                }
                System.out.println(line);
            }
        }
    }

    public static class VisitedPosition {
        Position position;
        int depth;

        public VisitedPosition(Position position, int depth) {
            this.position = position;
            this.depth = depth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VisitedPosition that = (VisitedPosition) o;
            return Objects.equal(position, that.position);
        }

        @Override
        public int hashCode() {
            return position.hashCode();
        }

        public Position getPosition() {
            return position;
        }

        public int getDepth() {
            return depth;
        }

        @Override
        public String toString() {
            return "VisitedPosition{" +
                    "position=" + position +
                    ", depth=" + depth +
                    '}';
        }
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Position move(Direction d) {
            switch (d) {
                case NORTH: return new Position(x, y-1);
                case SOUTH: return new Position(x, y+1);
                case WEST: return new Position(x-1, y);
                case EAST: return new Position(x+1, y);
                default: throw new IllegalArgumentException("Unknown direction " + d);
            }
        }

        public Direction directionToGetTo(Position target) {
            for (Direction d : Direction.values()) {
                if (target.equals(move(d))) {
                    return d;
                }
            }
            throw new IllegalArgumentException("Could not find move from " + this + " to " + target);
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
            return "[x=" + x + ", y=" + y + ']';
        }
    }

    public enum Direction {
        NORTH (1),
        SOUTH (2),
        WEST(3),
        EAST(4);

        private int code;

        Direction(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Item {
        WALL('\u2588'),
        EMPTY(' '),
        OXYGEN_SYSTEM ('*');

        private char character;

        Item(char character) {
            this.character = character;
        }

        public char getCharacter() {
            return character;
        }
    }

    public enum DroidOutput {
        WALL (0),
        EMPTY (1),
        OXYGEN_SYSTEM(2);

        private int code;

        DroidOutput(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static DroidOutput fromCode(int outputCode) {
            return Arrays.stream(DroidOutput.values())
                    .filter(v -> v.getCode() == outputCode)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown output code " + outputCode));
        }
    }
}