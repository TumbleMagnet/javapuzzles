package be.rouget.puzzles.adventofcode.year2021.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TrickShot {

    private static final Logger LOG = LogManager.getLogger(TrickShot.class);
    private static final Zone TARGET_ZONE = new Zone(new Position(143, -71), new Position(177, -106));
    private static final int MAX_VELOCITY = 1000;

    private final Zone targetZone;

    public static void main(String[] args) {
        TrickShot aoc = new TrickShot(TARGET_ZONE);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TrickShot(Zone targetZone) {
        this.targetZone = targetZone;
    }

    public long computeResultForPart1() {
        int maxHeight = 0;
        for (int x = 1; x <= targetZone.getLowerRight().getX(); x++) {
            for (int y = 0; y < MAX_VELOCITY; y++) {
                Optional<Integer> optionalHeight = hitsZoneAndReturnMaxHeight(x, y, this.targetZone);
                if (optionalHeight.isPresent()) {
                    maxHeight = Math.max(maxHeight, optionalHeight.get());
                }
            }
        }
        return maxHeight;
    }

    public long computeResultForPart2() {
        int count = 0;
        for (int x = 1; x <= targetZone.getLowerRight().getX(); x++) {
            for (int y = targetZone.getLowerRight().getY(); y < MAX_VELOCITY; y++) {
                if (hitsZone(x, y, this.targetZone)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean hitsZone(int xVelocity, int yVelocity, Zone zone) {
        return hitsZoneAndReturnMaxHeight(xVelocity, yVelocity, zone).isPresent();
    }

    public static Optional<Integer> hitsZoneAndReturnMaxHeight(int xVelocity, int yVelocity, Zone zone) {

        Position current = new Position(0, 0);
        int currentXVelocity = xVelocity;
        int currentYVelocity = yVelocity;
        int maxHeight = current.getY();

        while (true) {

            // new position
            current = new Position(current.getX() + currentXVelocity, current.getY() + currentYVelocity);
            maxHeight = Math.max(maxHeight, current.getY());

            // Hit?
            if (zone.isInZone(current)) {
                return Optional.of(maxHeight);
            }

            // give up when zone was missed
            if (current.getY() < zone.getLowerRight().getY()
                || (currentXVelocity > 0 && current.getX() > zone.getLowerRight().getX())
                || (currentXVelocity < 0 && current.getX() < zone.getUpperLeft().getX())
                || (currentXVelocity == 0 && (current.getX() > zone.getLowerRight().getX() || current.getX() < zone.getUpperLeft().getX()))) {
                return Optional.empty();
            }

            // update velocities
            if (currentXVelocity >= 1) {
                currentXVelocity -= 1;
            } else if (currentXVelocity <= -1) {
                currentXVelocity += 1;
            }
            currentYVelocity -=1;
        }
    }
}