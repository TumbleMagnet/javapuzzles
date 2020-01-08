package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import be.rouget.puzzles.adventofcode.year2019.map.Position;
import be.rouget.puzzles.adventofcode.year2019.map.VisitedPosition;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AoC2019Day20 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day20.class);
    private DonutMap map;

    public AoC2019Day20(List<String> input) {
        map = new DonutMap(input);
        map.printMap();
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day20_input.txt");
        AoC2019Day20 aoc = new AoC2019Day20(input);

        LOG.info("Starting search for part 1...");
        LOG.info("Result for part 1 is " + aoc.computeResultPart1());

        LOG.info("Starting search for part 2...");
        LOG.info("Result for part 2 is " + aoc.computeResultPart2());
    }

    public int computeResultPart2() {

        // Do a BFS search from start to end but with levels this time
        Queue<VisitedPositionWithLevel> queue = new LinkedList<>();
        Set<VisitedPositionWithLevel> visitedNodes = Sets.newHashSet();

        VisitedPositionWithLevel start = new VisitedPositionWithLevel(map.getStartPosition(), 0, 0);
        queue.add(start);
        visitedNodes.add(start);

        while (!queue.isEmpty()) {
            VisitedPositionWithLevel v = queue.remove();
            if (v.getPosition().equals(map.getEndPosition())
                && v.getLevel() == 0) {
                return v.getDistance();
            }
            List<PositionWithLevel> neighbours = map.getAccessibleNeighbours(v.getPosition(), v.getLevel());
            for (PositionWithLevel neighbour : neighbours) {
                VisitedPositionWithLevel newVisitedPosition = new VisitedPositionWithLevel(neighbour.getPosition(), neighbour.getLevel(), v.getDistance() + 1);
                if (!visitedNodes.contains(newVisitedPosition)) {
                    visitedNodes.add(newVisitedPosition);
                    queue.add(newVisitedPosition);
                }
            }
        }
        throw new IllegalStateException("Could not find path from start to end!");
    }

    public int computeResultPart1() {

        // Do a BFS search from start to end
        Queue<VisitedPosition> queue = new LinkedList<>();
        Set<VisitedPosition> visitedNodes = Sets.newHashSet();

        VisitedPosition start = new VisitedPosition(map.getStartPosition(), 0);
        queue.add(start);
        visitedNodes.add(start);

        while (!queue.isEmpty()) {
            VisitedPosition v = queue.remove();
            if (v.getPosition().equals(map.getEndPosition())) {
                return v.getDepth();
            }
            List<Position> neighbours = map.getAccessibleNeighbours(v.getPosition());
            for (Position neighbour : neighbours) {
                VisitedPosition newVisitedPosition = new VisitedPosition(neighbour, v.getDepth() + 1);
                if (!visitedNodes.contains(newVisitedPosition)) {
                    visitedNodes.add(newVisitedPosition);
                    queue.add(newVisitedPosition);
                }
            }

        }
        throw new IllegalStateException("Could not find path from start to end!");
    }

    public static class DonutMap {

        private static final char WALL_CHAR ='#';
        private static final char CORRIDOR_CHAR ='.';
        private static final char EMPTY_CHAR =' ';
        private static final char GATE_CHAR ='@';

        private static final int MARGIN_WIDTH = 2;
        private static final int MAZE_WIDTH = 35;

        private static final String LABEL_START = "AA";
        private static final String LABEL_END   = "ZZ";

        private Map<Position, Item> map = null;
        private int size = -1;
        private Position startPosition;
        private Position endPosition;


        public DonutMap(List<String> input) {
            parseMap(input);
        }

        private void parseMap(List<String> input) {

            size = input.size();
            map = Maps.newHashMap();

            char[][] charMap = inputToArray(input);

            // Do a first pass ignoring labels (only space, empty and walls)
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    char c = charMap[x][y];
                    Item item = toItem(x, y, c);
                    map.put(item.getPosition(), item);
                }
            }

            // Compute list of gates by scanning inner and outer edges
            Map<String, List<Gate>> gatesByLabel = Maps.newHashMap();

            // Compute outer corners
            Position outerTopLeft     = new Position(MARGIN_WIDTH, MARGIN_WIDTH);
            Position outerBottomLeft  = new Position(MARGIN_WIDTH, size- MARGIN_WIDTH -1);
            Position outerTopRight    = new Position(size- MARGIN_WIDTH -1, MARGIN_WIDTH);
            Position outerBottomRight = new Position(size- MARGIN_WIDTH -1, size- MARGIN_WIDTH -1);

            // Scan outer edges
            scanGatesOnVerticalLine(outerTopLeft, outerBottomLeft, Direction.LEFT, GateType.OUTER, charMap, gatesByLabel);
            scanGatesOnVerticalLine(outerTopRight, outerBottomRight, Direction.RIGHT,  GateType.OUTER, charMap, gatesByLabel);
            scanGatesOnHorizontalLine(outerTopLeft, outerTopRight, Direction.UP,  GateType.OUTER, charMap, gatesByLabel);
            scanGatesOnHorizontalLine(outerBottomLeft, outerBottomRight, Direction.DOWN, GateType.OUTER, charMap, gatesByLabel);

            // Compute inner corners
            Position innerTopLeft     = new Position(MARGIN_WIDTH + MAZE_WIDTH -1, MARGIN_WIDTH + MAZE_WIDTH -1);
            Position innerBottomLeft  = new Position(MARGIN_WIDTH + MAZE_WIDTH -1, size- MARGIN_WIDTH - MAZE_WIDTH);
            Position innerTopRight    = new Position(size- MARGIN_WIDTH - MAZE_WIDTH, MARGIN_WIDTH + MAZE_WIDTH -1);
            Position innerBottomRight = new Position(size- MARGIN_WIDTH - MAZE_WIDTH, size- MARGIN_WIDTH - MAZE_WIDTH);

            // Scan inner edges
            scanGatesOnVerticalLine(innerTopLeft, innerBottomLeft, Direction.RIGHT, GateType.INNER, charMap, gatesByLabel);
            scanGatesOnVerticalLine(innerTopRight, innerBottomRight, Direction.LEFT, GateType.INNER, charMap, gatesByLabel);
            scanGatesOnHorizontalLine(innerTopLeft, innerTopRight, Direction.DOWN, GateType.INNER, charMap, gatesByLabel);
            scanGatesOnHorizontalLine(innerBottomLeft, innerBottomRight, Direction.UP, GateType.INNER, charMap, gatesByLabel);

            // Handle special labels for start/end
            startPosition = gatesByLabel.get(LABEL_START).get(0).getInPosition();
            endPosition = gatesByLabel.get(LABEL_END).get(0).getInPosition();
            gatesByLabel.remove(LABEL_START);
            gatesByLabel.remove(LABEL_END);

            // Add normal gates to the map
            for (Map.Entry<String, List<Gate>> gateEntry: gatesByLabel.entrySet()) {
                Gate gate1 = gateEntry.getValue().get(0);
                Gate gate2 = gateEntry.getValue().get(1);

                map.put(gate1.getOutPosition(), Item.gate(gate1.getOutPosition(), gate2.getInPosition(), gateEntry.getKey(), gate1.getGateType()));
                map.put(gate2.getOutPosition(), Item.gate(gate2.getOutPosition(), gate1.getInPosition(), gateEntry.getKey(), gate2.getGateType()));
            }
        }

        private void scanGatesOnHorizontalLine(Position left, Position right, Direction labelDirection, GateType gateType, char[][] charMap, Map<String, List<Gate>> gatesByLabel) {
            int startX = left.getX();
            int endX = right.getX();
            int y = left.getY();
            for (int x=startX; x<=endX; x++) {
                Item item = map.get(new Position(x, y));
                if (item.getType() == ItemType.CORRIDOR) {
                    Gate gate = extractGate(item.getPosition(), labelDirection, gateType, charMap);
                    mergeGate(gate, gatesByLabel);
                }
            }
        }

        private void scanGatesOnVerticalLine(Position top, Position bottom, Direction labelDirection, GateType gateType, char[][] charMap, Map<String, List<Gate>> gatesByLabel) {
            int startY = top.getY();
            int endY = bottom.getY();
            int x = top.getX();
            for (int y = startY; y <= endY; y++) {
                Item item = map.get(new Position(x, y));
                if (item.getType() == ItemType.CORRIDOR) {
                    Gate gate = extractGate(item.getPosition(), labelDirection, gateType, charMap);
                    mergeGate(gate, gatesByLabel);
                }
            }
        }

        private static void mergeGate(Gate gate, Map<String, List<Gate>> gatesByLabel) {
            List<Gate> existingGateWithLabel = gatesByLabel.get(gate.getLabel());
            if (existingGateWithLabel == null) {
                gatesByLabel.put(gate.getLabel(), Lists.newArrayList(gate));
            }
            else {
                existingGateWithLabel.add(gate);
                if (existingGateWithLabel.size() > 2) {
                    throw new IllegalStateException("Label " + gate.getLabel() + " has more than two gates " + existingGateWithLabel);
                }
            }
        }

        private static Gate extractGate(Position corridorPosition, Direction direction, GateType gateType, char[][] charMap) {

            // Compute label
            Position label1 = corridorPosition.move(direction);
            Position label2 = label1.move(direction);

            String label = null;
            switch (direction) {
                case UP:
                case LEFT:
                    label = toLabel(charMap, label2, label1); break; // Reversed
                case DOWN:
                case RIGHT:
                    label = toLabel(charMap, label1, label2); break; // In order
                default:
                    throw new IllegalArgumentException("Unsupported direction " + direction);
            }
            if (label.equals(LABEL_START) || label.equals(LABEL_END)) {
                return new Gate(label, corridorPosition, null, null);
            }
            else {
                return new Gate(label, corridorPosition, corridorPosition.move(direction), gateType);
            }
        }

        public static String toLabel(char[][] charMap, Position p1, Position p2) {
            return "" + charMap[p1.getX()][p1.getY()] + charMap[p2.getX()][p2.getY()];
        }

        public Item toItem(int x, int y, char c) {
            Position p = new Position(x, y);
            if (c == WALL_CHAR) {
                return Item.wall(p);
            } else if (c == CORRIDOR_CHAR) {
                return Item.corridor(p);
            }
            else {
                // Ignore difference between empty cells and cells with gate labels
                return Item.empty(p);
            }
        }

        private char[][] inputToArray(List<String> input) {
            char[][] array = new char[size][size];

            for (int y = 0; y < size; y++) {
                String line = input.get(y);
                for (int x = 0; x < size; x++) {
                    array[x][y] = line.charAt(x);
                }
            }
            return array;
        }

        public void printMap() {
            List<Item> gates = Lists.newArrayList();
            for (int y = 0; y < size; y++) {
                String line = "";
                for (int x = 0; x < size; x++) {
                    Item item = map.get(new Position(x, y));
                    line += toPrintableChar(item);
                    if (item.getType() == ItemType.GATE) {
                        gates.add(item);
                    }
                }
                System.out.println(line);
            }
            System.out.println("Start position is: " + startPosition);
            System.out.println("End position is: " + endPosition);
            System.out.println("Gates:");
            gates.stream().forEach(g -> System.out.println("  " + g.getGateLabel() + ": " + g.getPosition() + " -> " + g.getGateTarget()));
        }

        private char toPrintableChar(Item item) {
            switch (item.getType()) {
                case WALL: return WALL_CHAR;
                case CORRIDOR: return CORRIDOR_CHAR;
                case EMPTY: return EMPTY_CHAR;
                case GATE: return GATE_CHAR;
                default:
                    throw new IllegalArgumentException("Unsupported item type " + item.getType());
            }
        }

        public Position getStartPosition() {
            return startPosition;
        }

        public Position getEndPosition() {
            return endPosition;
        }

        public List<Position> getAccessibleNeighbours(Position current) {

            List<Position> neighbours = Lists.newArrayList();
            for (Direction direction : Direction.values()) {
                Position neighbourPosition = current.move(direction);
                Item neighbourItem = map.get(neighbourPosition);
                if (neighbourItem.getType() == ItemType.CORRIDOR) {
                    neighbours.add(neighbourPosition);
                }
                else if (neighbourItem.getType() == ItemType.GATE) {
                    neighbours.add(neighbourItem.getGateTarget());
                }
            }
            return neighbours;
        }

        public List<PositionWithLevel> getAccessibleNeighbours(Position current, int level) {
            List<PositionWithLevel> neighbours = Lists.newArrayList();
            for (Direction direction : Direction.values()) {
                Position neighbourPosition = current.move(direction);
                Item neighbourItem = map.get(neighbourPosition);
                if (neighbourItem.getType() == ItemType.CORRIDOR) {
                    neighbours.add(new PositionWithLevel(neighbourPosition, level));
                }
                else if (neighbourItem.getType() == ItemType.GATE) {
                    if (neighbourItem.getGateType() == GateType.INNER) {
                        neighbours.add(new PositionWithLevel(neighbourItem.getGateTarget(), level+1));
                    }
                    else if (level > 0){
                        neighbours.add(new PositionWithLevel(neighbourItem.getGateTarget(), level-1));
                    }
                }
            }
            return neighbours;

        }

        private static class Gate {
            private String label;
            private Position inPosition;
            private Position outPosition;
            private GateType gateType;

            public Gate(String label, Position in, Position out, GateType gateType) {
                if (label.length() != 2) {
                    throw new IllegalArgumentException("Invalid label " + label);
                }
                this.label = label;
                inPosition = in;
                outPosition = out;
                this.gateType = gateType;
            }

            public String getLabel() {
                return label;
            }

            public Position getInPosition() {
                return inPosition;
            }

            public Position getOutPosition() {
                return outPosition;
            }

            public GateType getGateType() {
                return gateType;
            }

            @Override
            public String toString() {
                return "Gate{" +
                        "label='" + label + '\'' +
                        ", inPosition=" + inPosition +
                        ", outPosition=" + outPosition +
                        '}';
            }
        }
    }

    public static class Item {
        private Position position;
        private ItemType type;
        private Position gateTarget;
        private String gateLabel;
        private GateType gateType;

        private Item(Position position, ItemType type) {
            this(position, type, null, null, null);
        }

        private Item(Position position, ItemType type, Position gateTarget, String gateLabel, GateType gateType) {
            this.position = position;
            this.type = type;
            this.gateTarget = gateTarget;
            this.gateLabel = gateLabel;
            this.gateType = gateType;
        }

        public Position getPosition() {
            return position;
        }

        public ItemType getType() {
            return type;
        }

        public Position getGateTarget() {
            return gateTarget;
        }

        public String getGateLabel() {
            return gateLabel;
        }

        public GateType getGateType() {
            return gateType;
        }

        public static Item corridor(Position p) {
            return new Item(p, ItemType.CORRIDOR);
        }
        public static Item wall(Position p) {
            return new Item(p, ItemType.WALL);
        }
        public static Item empty(Position p) {
            return new Item(p, ItemType.EMPTY);
        }
        public static Item gate(Position from, Position to, String label, GateType gateType) {
            return new Item(from, ItemType.GATE, to, label, gateType);
        }
    }

    public enum ItemType {
        CORRIDOR,
        WALL,
        EMPTY,
        GATE;
    }

    public enum GateType {
        INNER,
        OUTER;
    }

    public static class PositionWithLevel {
        Position position;
        int level;

        public PositionWithLevel(Position position, int level) {
            this.position = position;
            this.level = level;
        }

        public Position getPosition() {
            return position;
        }

        public int getLevel() {
            return level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionWithLevel that = (PositionWithLevel) o;
            return level == that.level &&
                    Objects.equal(position, that.position);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(position, level);
        }

        @Override
        public String toString() {
            return "PositionWithLevel{" +
                    "position=" + position +
                    ", level=" + level +
                    '}';
        }
    }

    public static class VisitedPositionWithLevel {
        Position position;
        int level;
        int distance;

        public VisitedPositionWithLevel(Position position, int level, int distance) {
            this.position = position;
            this.level = level;
            this.distance = distance;
        }

        public Position getPosition() {
            return position;
        }

        public int getLevel() {
            return level;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VisitedPositionWithLevel that = (VisitedPositionWithLevel) o;
            return level == that.level &&
                    Objects.equal(position, that.position);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(position, level);
        }

        @Override
        public String toString() {
            return "VisitedPositionWithLevel{" +
                    "position=" + position +
                    ", level=" + level +
                    ", distance=" + distance +
                    '}';
        }
    }
}