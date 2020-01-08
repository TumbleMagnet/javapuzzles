package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AoC2019Day10 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day10.class);

    private int maxX = -1;
    private int maxY = -1;

    public static void main(String[] args) {

        List<String> input = ResourceUtils.readLines("aoc_2019_day10_input.txt");
        AoC2019Day10 aoc = new AoC2019Day10();
        int result = aoc.computeResult(input);
        LOG.info("Result is " + result);
    }

    private static double angle(int x, int y) {

        double angle = 0;
        if (x >= 0) {
            angle = Math.atan2(x, y) / (2 * Math.PI) * 360;
        }
        else {
            angle = (2* Math.PI + Math.atan2(x, y)) / (2*Math.PI) * 360;
        }
        return angle;
    }

    public int computeResult(List<String> input) {
        maxY = input.size()-1;
        maxX = input.get(0).length()-1;
        LOG.info("Size: " + maxX + " * " + maxY);
        Table<Integer, Integer, Asteroid> map = parseMap(input);
        LOG.info("Map contains " + map.values().size() + " asteroids");

        Asteroid base = map.get(11, 13);

        Asteroid asteroid200 = findAsteroid(base, map);
        return asteroid200.getX()*100+asteroid200.getY();

//        return map.values().stream().mapToInt(a -> countVisible(a, map)).max().orElseThrow(()-> new RuntimeException("Cannot compute max"));
    }

    private Asteroid findAsteroid(Asteroid base, Table<Integer, Integer, Asteroid> map) {

        Set<Asteroid> otherAsteroids = map.values().stream().filter(a -> !a.equals(base)).collect(Collectors.toSet());
        Set<Asteroid> remainingAsteroids = Sets.newHashSet(otherAsteroids);
        Map<Double, List<Asteroid>> lines = Maps.newHashMap();
        for (Asteroid other : otherAsteroids) {

            if (!remainingAsteroids.contains(other)) {
                continue;
            }

            int xDiff = other.getX() - base.getX();
            int yDiff = other.getY() - base.getY();
            int gcd = new BigInteger(""+xDiff).gcd(new BigInteger(""+yDiff)).intValue();
            int deltaX = 0;
            int deltaY = 0;
            if (gcd == 0) {
                deltaX = xDiff != 0 ? 1 : 0;
                deltaY = yDiff != 0 ? 1 : 0;
            }
            else {
                deltaX = xDiff / gcd;
                deltaY = yDiff / gcd;
            }

            double angle = angle(deltaX, deltaY);
            double correctedAngle = deltaX >= 0 ? 180 - angle : 540 - angle;
            List<Asteroid> line = Lists.newArrayList();
            int stepIndex = 1;
            while (true) {
                int x = base.getX() + deltaX * stepIndex;
                int y = base.getY() + deltaY * stepIndex;
                if (x > maxX || y > maxY || x < 0 || y < 0) {
                    break;
                }
                Asteroid hitAsteroid = map.get(x, y);
                if (hitAsteroid != null) {
                    line.add(hitAsteroid);
                    remainingAsteroids.remove(hitAsteroid);
                }
                lines.put(correctedAngle, line);

                stepIndex++;
            }
        }

        List<Double> angles = lines.keySet().stream().collect(Collectors.toList());
        Collections.sort(angles);
        int count = 0;
        int index = 0;
        while (true) {
            Double angle = angles.get(index);
            List<Asteroid> asteroids = lines.get(angle);
            if (!asteroids.isEmpty()) {
                Asteroid destroyed = asteroids.get(0);
                count++;
                if (count == 200) {
                    return destroyed;
                }
                asteroids.remove(destroyed);
            }
            index++;
            if (index >= angles.size()) {
                index = 0;
            }
        }
    }


    private int countVisible(Asteroid candidate, Table<Integer, Integer, Asteroid> map) {

        Set<Asteroid> otherAsteroids = map.values().stream().filter(a -> !a.equals(candidate)).collect(Collectors.toSet());
        Set<Asteroid> visibleAsteroids = Sets.newHashSet(otherAsteroids);
        for (Asteroid other : otherAsteroids) {

            if (!visibleAsteroids.contains(other)) {
                continue;
            }

            int xDiff = other.getX() - candidate.getX();
            int yDiff = other.getY() - candidate.getY();
            int gcd = new BigInteger(""+xDiff).gcd(new BigInteger(""+yDiff)).intValue();
            int deltaX = 0;
            int deltaY = 0;
            if (gcd == 0) {
                deltaX = xDiff != 0 ? 1 : 0;
                deltaY = yDiff != 0 ? 1 : 0;
            }
            else {
                deltaX = xDiff / gcd;
                deltaY = yDiff / gcd;
            }

            int stepIndex = 1;
            boolean firstHit = true;
            while (true) {
                int x = candidate.getX() + deltaX * stepIndex;
                int y = candidate.getY() + deltaY * stepIndex;
                if (x > maxX || y > maxY || x < 0 || y < 0) {
                    break;
                }
                Asteroid hitAsteroid = map.get(x, y);
                if (hitAsteroid != null) {
                    if (firstHit) {
                        // Visible
                        firstHit = false;
                    }
                    else {
                        // Not visible
                        visibleAsteroids.remove(hitAsteroid);
                    }
                }
                stepIndex++;
            }
        }
        int count = visibleAsteroids.size();
        LOG.info("Number of asteroids visible from " + candidate + ": " + count);
        return count;
    }

    private static Table<Integer, Integer, Asteroid> parseMap(List<String> input) {

        Table<Integer, Integer, Asteroid> map = HashBasedTable.create();
        int y = 0;
        for (String line: input) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c== '#') {
                    map.put(x, y, new Asteroid(x, y));
                }
                x++;
            }
            y++;
        }
        return map;
    }



    public static class Asteroid {
        private int x;
        private int y;
//        private boolean isVisible;


        public Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
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
            Asteroid asteroid = (Asteroid) o;
            return x == asteroid.x &&
                    y == asteroid.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }

        @Override
        public String toString() {
            return "Asteroid{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}