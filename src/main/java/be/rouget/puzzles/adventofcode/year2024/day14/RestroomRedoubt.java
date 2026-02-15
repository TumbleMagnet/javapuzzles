package be.rouget.puzzles.adventofcode.year2024.day14;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


public class RestroomRedoubt {

    private static final Logger LOG = LogManager.getLogger(RestroomRedoubt.class);

    private final Room room;
    private final List<String> input;

    @SuppressWarnings("java:S2629") // OK to compute results when logging
    static void main() {
        List<String> input = SolverUtils.readInput(RestroomRedoubt.class);
        RestroomRedoubt aoc = new RestroomRedoubt(new Room(101, 103), input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public RestroomRedoubt(Room room, List<String> input) {
        this.room = room;
        LOG.info("Room:{} ", room);
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        List<Robot> robots = input.stream()
                .map(Robot::parse)
                .toList();
        LOG.info("Parsed {} robots...", robots.size());
        for (int i = 0; i < 100; i++) {
            robots.forEach(robot -> robot.move(room));
        }
        printRoom(robots);
        return computeSecurityFactor(robots);
    }

    private long computeSecurityFactor(List<Robot> robots) {
        Map<RoomQuadrant, Long> countPerQuadrant = robots.stream()
                .map(Robot::getCoordinates)
                .map(room::getQuadrant)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(quadrant -> quadrant, Collectors.counting()));
        return countPerQuadrant.values().stream()
                .mapToLong(Long::longValue)
                .reduce(1, Math::multiplyExact);
    }

    public long computeResultForPart2() {
        List<Robot> robots = input.stream()
                .map(Robot::parse)
                .toList();
        LOG.info("Parsed {} robots...", robots.size());

        // Hindsight: the security factor of part 1 is somehow a measure of entropy, so we expect the Christmas tree
        // to correspond to the robot positioning with the minimal security factor.
        // Algorithm: print configurations with a lower security factor than the current minimum, until the correct one
        // is found (it takes 5-10 tries).
        long minSecurityFactor = Long.MAX_VALUE;
        Scanner in = new Scanner(System.in);
        long numberOfMoves = 0;
        while (true) {
            robots.forEach(robot -> robot.move(room));
            numberOfMoves++;
            long securityFactor = computeSecurityFactor(robots);
            if (securityFactor < minSecurityFactor) {
                minSecurityFactor = securityFactor;
                printRoom(robots);
                String answer = in.next();
                if (answer.equals("x")) {
                    // Based on printed output, user confirmed that the tree was found
                    return numberOfMoves;
                }
            }
        }
    }

    @SuppressWarnings("java:S106") // Ok to use standard output
    private void printRoom(List<Robot> robots) {
        System.out.println("---------------------------");

        Set<Coordinates> robotPositions = robots.stream()
                .map(Robot::getCoordinates)
                .collect(Collectors.toSet());

        for (int y = 0; y < room.height(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < room.width(); x++) {
                Coordinates positionToDraw = new Coordinates(x, y);
                if (robotPositions.contains(positionToDraw)) {
                    line.append("#");
                } else {
                    line.append(" ");
                }
            }
            System.out.println(line);
        }

        System.out.println("---------------------------");
    }
}