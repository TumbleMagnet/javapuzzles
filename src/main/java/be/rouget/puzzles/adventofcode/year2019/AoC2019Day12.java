package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

import static java.lang.Math.abs;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class AoC2019Day12 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day12.class);

    public static void main(String[] args) {

        List<String> input = ResourceUtils.readLines("aoc_2019_day12_input.txt");
        long result = computeResult2(input);
        LOG.info("Result is " + result);
    }

    public static int computeResult(List<String> input) {

        Moon[] moons = new Moon[] {
                new Moon(new Position(-8,-9, -7)),
                new Moon(new Position(-5, 2, -1)),
                new Moon(new Position(11, 8, -14)),
                new Moon(new Position(1, -4, -11)),
        };
        LOG.info("Starting status:");
        printStatus(moons);
        for (int i = 0; i < 1000; i++) {
            simulateStep(moons);
        }
        LOG.info("final status:");
        printStatus(moons);
        return computeTotalEnergy(moons);
    }

    public static long computeResult2(List<String> input) {

        Position[] startingPositions = new Position[] {
                new Position(-8,-9, -7),
                new Position(-5, 2, -1),
                new Position(11, 8, -14),
                new Position(1, -4, -11),
        };

        Moon[] moons = new Moon[] {
                new Moon(startingPositions[0]),
                new Moon(startingPositions[1]),
                new Moon(startingPositions[2]),
                new Moon(startingPositions[3]),
        };
        LOG.info("Starting status:");
        printStatus(moons);

        int stepsForX = computeStepsForDimension(startingPositions, moons, Dimension.X);
        int stepsForY = computeStepsForDimension(startingPositions, moons, Dimension.Y);
        int stepsForZ = computeStepsForDimension(startingPositions, moons, Dimension.Z);
        LOG.info("X=" + stepsForX);
        LOG.info("Y=" + stepsForY);
        LOG.info("Z=" + stepsForZ);
        return lcm(lcm(stepsForX, stepsForY), stepsForZ);
    }

    private static long lcm(long a, long b) {
        long gcd = new BigInteger(""+a).gcd(new BigInteger(""+b)).longValue();
        return (a*b)/gcd;
    }

    private static int computeStepsForDimension(Position[] startingPositions, Moon[] moons, Dimension dimension) {
        int step = 0;
        while (true) {
            step++;
            simulateStep(moons, dimension);
            if (backToStart(moons, startingPositions)) {
                return step;
            }
        }
    }

    private static boolean backToStart(Moon[] moons, Position[] startingPositions) {
        for (int i=0; i<moons.length; i++) {
            Velocity velocity = moons[i].getVelocity();
            if (velocity.getX() != 0 || velocity.getY() != 0 || velocity.getZ() != 0) {
                return false;
            }
            Position p1 = moons[i].getPosition();
            Position p2 = startingPositions[i];
            if ((p1.getX() != p2.getX())
                    || (p1.getY() != p2.getY())
                    || (p1.getZ() != p2.getZ())) {
                return false;
            }
        }
        return true;
    }

    public static void printStatus(Moon[] moons) {
        for (Moon moon: moons) {
            System.out.println(moon);
        }
        System.out.println();
    }

    public static void simulateStep(Moon[] moons) {
        for (Moon moon: moons) {
            moon.updateVelocity(moons);
        }
        for (Moon moon: moons) {
            moon.updatePosition();
        }
    }

    public static void simulateStep(Moon[] moons, Dimension dimension) {
        for (Moon moon: moons) {
            moon.updateVelocity(moons, dimension);
        }
        for (Moon moon: moons) {
            moon.updatePosition(dimension);
        }
    }

    public static int computeTotalEnergy(Moon[] moons) {
        int total = 0;
        for (Moon moon : moons) {
            total += moon.getEnergy();
        }
        return total;
    }

    public static class Moon {
        private Position position;
        private Velocity velocity;

        public Moon(Position position, Velocity velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public Moon(Position position) {
            this(position, new Velocity(0,0,0));
        }

        public Position getPosition() {
            return position;
        }

        public Velocity getVelocity() {
            return velocity;
        }

        public void updateVelocity(Moon[] allMoons) {
            for (Moon otherMoon : allMoons) {
                if (otherMoon == this) {
                    continue;
                }
                int vx = velocity.getX() + velocityUpdate(position.getX(), otherMoon.getPosition().getX());
                int vy = velocity.getY() + velocityUpdate(position.getY(), otherMoon.getPosition().getY());
                int vz = velocity.getZ() + velocityUpdate(position.getZ(), otherMoon.getPosition().getZ());
                velocity = new Velocity(vx, vy, vz);
            }
        }

        public void updateVelocity(Moon[] allMoons, Dimension dimension) {
            for (Moon otherMoon : allMoons) {
                if (otherMoon == this) {
                    continue;
                }
                int vx = velocity.getX();
                int vy = velocity.getY();
                int vz = velocity.getZ();
                switch (dimension) {
                    case X: vx = velocity.getX() + velocityUpdate(position.getX(), otherMoon.getPosition().getX()); break;
                    case Y: vy = velocity.getY() + velocityUpdate(position.getY(), otherMoon.getPosition().getY()); break;
                    case Z: vz = velocity.getZ() + velocityUpdate(position.getZ(), otherMoon.getPosition().getZ()); break;
                }
                velocity = new Velocity(vx, vy, vz);
            }
        }

        private static int velocityUpdate(int myCoord, int otherCoord) {
            if (myCoord < otherCoord) {
                return 1;
            }
            else if (myCoord > otherCoord) {
                return -1;
            }
            else {
                return 0;
            }


        }

        public void updatePosition() {
            position = new Position(
                position.getX() + velocity.getX(),
                position.getY() + velocity.getY(),
                position.getZ() + velocity.getZ()
            );
        }

        public void updatePosition(Dimension d) {
            int x = position.getX();
            int y = position.getY();
            int z = position.getZ();
            switch (d) {
                case X: x = position.getX() + velocity.getX(); break;
                case Y: y = position.getY() + velocity.getY(); break;
                case Z: z = position.getZ() + velocity.getZ(); break;
            }
            position = new Position(x,y,z);
        }
        public int getEnergy() {
            int pot = abs(position.getX()) + abs(position.getY()) + abs(position.getZ());
            int kin = abs(velocity.getX()) + abs(velocity.getY()) + abs(velocity.getZ());
            return pot*kin;
        }

        @Override
        public String toString() {
            return position + ", " + velocity;
        }
    }

    public static class Position {
        int x=0;
        int y=0;
        int z=0;

        public Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public String toString() {
            return "pos=<" +
                    "x=" + format(x) +
                    ", y=" + format(y) +
                    ", z=" + format(z) +
                    '>';
        }
    }

    public static class Velocity {
        int x=0;
        int y=0;
        int z=0;

        public Velocity(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public String toString() {
            return "vel=<" +
                    "x=" + format(x) +
                    ", y=" + format(y) +
                    ", z=" + format(z) +
                    '>';
        }

    }

    private static String format(int i) {
        return leftPad(""+ i, 3, " ");
    }

    private static enum Dimension { X,Y, Z}
}