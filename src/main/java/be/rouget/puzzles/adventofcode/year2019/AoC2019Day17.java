package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.AsciiComputer;
import be.rouget.puzzles.adventofcode.year2019.computer.Computer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import be.rouget.puzzles.adventofcode.year2019.map.Position;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AoC2019Day17 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day17.class);

    private Map<Position, Item> map = Maps.newHashMap();
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    private String program;

    private Position robotPosition;
    private Direction robotDirection;

    public static void main(String[] args) {
        String input = ResourceUtils.readIntoString("aoc_2019_day17_input.txt");
        AoC2019Day17 aoc = new AoC2019Day17(input);
        LOG.info("Result 1 is " + aoc.computePart1());
        LOG.info("Result 2 is " + aoc.computePart2());
    }

    public AoC2019Day17(String input) {
        this.program = input;
    }

    public long computePart2() {
        AsciiComputer computer = new AsciiComputer(program);
        computer.setMemoryAtPosition(0, 2);
        long[] inputValues = computeInputValues();
        LOG.info("Input values: " + Arrays.toString(inputValues));
        ComputerState state = computer.run(Arrays.asList(getInputStrings()));
        LOG.info("Computer state is " + state);
        List<String> output = computer.getOutput();
        LOG.info("Computer output: ");
        output.forEach(System.out::println);
        return Long.parseLong(output.get(output.size()-1));
    }

    private long[] computeInputValues() {
        String[] inputLines = getInputStrings();
        return Arrays.stream(inputLines)
                .flatMap(line -> toAsciiIntCode(line).stream())
                .mapToLong(Long::longValue).toArray();
    }

    private String[] getInputStrings() {
        return new String[]{
                    "A,A,B,C,B,C,B,C,B,A",
                    "L,10,L,8,R,8,L,8,R,6",
                    "R,6,R,8,R,8",
                    "R,6,R,6,L,8,L,10",
                    "n"
            };
    }

    private List<Long> toAsciiIntCode(String line) {

        List<Long> asciiCodes = Lists.newArrayList();
        char[] chars = line.toCharArray();
        for (char c: line.toCharArray()) {
            asciiCodes.add(Long.valueOf((int) c));
        }
        asciiCodes.add(10L);
        return asciiCodes;
    }

    public int computePart1() {
        Computer computer = new Computer(program);
        ComputerState state = computer.run();
        LOG.info("Computer state is " + state);
        List<Integer> output = computer.getOutput().stream().map(Integer::parseInt).collect(Collectors.toList());

        Position screenPosition = new Position(0, 0);
        for (Integer intCode: output) {

            MapCharacters mapCharacter = MapCharacters.fromIntCode(intCode);

            // Record map contents
            switch (mapCharacter) {
                case EMPTY:
                    map.put(screenPosition, Item.EMPTY);
                    break;
                case SCAFFOLD:
                    map.put(screenPosition, Item.SCAFFOLD);
                    break;
                case ROBOT_DOWN:
                    map.put(screenPosition, Item.SCAFFOLD);
                    setRobotInfo(screenPosition, Direction.DOWN);
                    break;
                case ROBOT_UP:
                    map.put(screenPosition, Item.SCAFFOLD);
                    setRobotInfo(screenPosition, Direction.UP);
                    break;
                case ROBOT_LEFT:
                    map.put(screenPosition, Item.SCAFFOLD);
                    setRobotInfo(screenPosition, Direction.LEFT);
                    break;
                case ROBOT_RIGHT:
                    map.put(screenPosition, Item.SCAFFOLD);
                    setRobotInfo(screenPosition, Direction.RIGHT);
                    break;
            }

            // Move to next screen position
            if (mapCharacter == MapCharacters.NEW_LINE) {
                screenPosition = startNewLine(screenPosition);
            }
            else {
                screenPosition = getNextPositionOnSameLine(screenPosition);
            }
        }
        computeMapSize();

        printMap();
        LOG.info("Robot is at position " + robotPosition + " and facing " + robotDirection);

        // Compute path for robot to cover the complete scaffolding
        String path = computeRobotPath(new RobotPosition(robotPosition, robotDirection));
        LOG.info("Robot path: " + path);

        // Compute alignment parameters of intersections
        int sum = map.keySet().stream().filter(p -> isIntersection(p)).mapToInt(p -> p.getX() * p.getY()).sum();
        return sum;
    }

    private String computeRobotPath(RobotPosition robotPosition) {

        String path = "";
        RobotPosition current = robotPosition;
        int movesInCurrentDirection = 0;
        while (true) {

            if (isScaffold(nextPosition(current.getPosition(), current.getDirection()))) {
                current = new RobotPosition(nextPosition(current.getPosition(), current.getDirection()), current.getDirection());
                movesInCurrentDirection++;
            }
            else if (isScaffold(nextPosition(current.getPosition(), current.getDirection().turnLeft()))) {
                current = new RobotPosition(current.getPosition(), current.getDirection().turnLeft());
                if (movesInCurrentDirection > 0) {
                    path += movesInCurrentDirection + ",";
                    movesInCurrentDirection = 0;
                }
                path += "L,";

            }
            else if (isScaffold(nextPosition(current.getPosition(), current.getDirection().turnRight()))) {
                current = new RobotPosition(current.getPosition(), current.getDirection().turnRight());
                if (movesInCurrentDirection > 0) {
                    path += movesInCurrentDirection + ",";
                    movesInCurrentDirection = 0;
                }
                path += "R,";

            }
            else {
                path += movesInCurrentDirection;
                break;
            }
        }
        return path;
    }

    private String computePath(RobotPosition current, RobotPosition next) {
        if (next.getDirection() == current.getDirection()) {
            return "1,";
        } else if (next.getDirection() == current.getDirection().turnLeft()) {
            return "L,1,";
        } else if (next.getDirection() == current.getDirection().turnRight()) {
            return "R,1,";
        }
        throw new IllegalArgumentException("Cannot compute path element from " + current + " to " + next);
    }

    private RobotPosition computeNextRobotPosition(RobotPosition current) {

        RobotPosition next = nextPositionInSameDirection(current);
        if (isScaffold(next.getPosition())) {
            return next;
        }

        // Turn left?
        Direction newDirection = current.getDirection().turnLeft();
        next = nextPositionInDirection(current, newDirection);
        if (isScaffold(next.getPosition())) {
            return next;
        }

        // Turn Right?
        newDirection = current.getDirection().turnRight();
        next = nextPositionInDirection(current, newDirection);
        if (isScaffold(next.getPosition())) {
            return next;
        }

        return null;
    }

    private RobotPosition nextPositionInSameDirection(RobotPosition p) {
        return nextPositionInDirection(p, p.getDirection());
    }

    private RobotPosition nextPositionInDirection(RobotPosition p, Direction newDirection) {
        Position nextPosition = nextPosition(p.getPosition(), newDirection);
        return new RobotPosition(nextPosition, newDirection);
    }


    private void computeMapSize() {
        minX = map.keySet().stream().mapToInt(Position::getX).min().orElseThrow(IllegalStateException::new);
        maxX = map.keySet().stream().mapToInt(Position::getX).max().orElseThrow(IllegalStateException::new);
        minY = map.keySet().stream().mapToInt(Position::getY).min().orElseThrow(IllegalStateException::new);
        maxY = map.keySet().stream().mapToInt(Position::getY).max().orElseThrow(IllegalStateException::new);
    }

    private boolean isIntersection(Position p) {

        // Intersections are not on edges
        if ((p.getX() == minX) || (p.getX() == maxX)
            || (p.getY() == minY) || (p.getY() == maxY)) {
            return false;
        }

        return isScaffold(p)
                && isScaffold(nextPosition(p, Direction.UP))
                && isScaffold(nextPosition(p, Direction.RIGHT))
                && isScaffold(nextPosition(p, Direction.DOWN))
                && isScaffold(nextPosition(p, Direction.LEFT))
                ;
    }

    private boolean isScaffold(Position position) {
        return isInMap(position) && map.get(position) == Item.SCAFFOLD;
    }

    private boolean isInMap(Position p) {
        return (p.getX() >= minX) && (p.getX() <= maxX) && (p.getY() >= minY) && (p.getY() <= maxY);
    }

    private Position nextPosition(Position p, Direction d) {
        switch (d) {
            case UP : return new Position(p.getX(), p.getY() - 1);
            case DOWN : return new Position(p.getX(), p.getY() + 1);
            case LEFT : return new Position(p.getX() - 1,  p.getY());
            case RIGHT : return new Position(p.getX() + 1, p.getY());
            default:
                throw new IllegalArgumentException("Unknown direction " + d);
        }
    }

    private void setRobotInfo(Position p, Direction d) {
        robotPosition = p;
        robotDirection = d;
    }

    private Position startNewLine(Position screenPosition) {
        return new Position(0, screenPosition.getY() + 1);
    }

    private Position getNextPositionOnSameLine(Position screenPosition) {
        return new Position(screenPosition.getX() + 1, screenPosition.getY());
    }



    private void printMap() {
        Position upperLeft = new Position(minX, minY);
        Position lowerRight = new Position(maxX, maxY);

        for (int y=upperLeft.getY(); y<=lowerRight.getY(); y++) {
            String line = "";
            for (int x=upperLeft.getX(); x<=lowerRight.getX(); x++) {
                Item item = map.get(new Position(x, y));
                if (item == Item.EMPTY) {
                    line += MapCharacters.EMPTY.getPrintCharacter();
                }
                else if (item == Item.SCAFFOLD) {
                    line += MapCharacters.SCAFFOLD.getPrintCharacter();
                }
            }
            System.out.println(line);
        }
    }

    public enum MapCharacters {
        NEW_LINE(10, '\n'),
        SCAFFOLD(35, '#'),
        EMPTY(46, '.'),
        ROBOT_UP(94, '^'),
        ROBOT_DOWN(118, 'v'),
        ROBOT_LEFT(60, '<'),
        ROBOT_RIGHT(62, '>'),
        ;

        MapCharacters(int intCode, char printCharacter) {
            this.intCode = intCode;
            this.printCharacter = printCharacter;
        }

        private int intCode;
        private char printCharacter;

        public int getIntCode() {
            return intCode;
        }

        public char getPrintCharacter() {
            return printCharacter;
        }

        public static MapCharacters fromIntCode(int intCode) {
            for (MapCharacters mapChar: MapCharacters.values()) {
                if (mapChar.getIntCode() == intCode) {
                    return mapChar;
                }
            }
            throw new IllegalArgumentException("Invalid intCode " + intCode);
        }

    }

    public enum Item {
        SCAFFOLD, EMPTY;
    }

    public static class RobotPosition {

        private Position position;
        private Direction direction;

        public RobotPosition(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public Position getPosition() {
            return position;
        }

        public Direction getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return "RobotPosition{" +
                    "position=" + position +
                    ", direction=" + direction +
                    '}';
        }
    }
}