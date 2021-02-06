package be.rouget.puzzles.adventofcode.year2015.day1to5;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AdventOfCode2015Day03 {

    private static Logger LOG = LogManager.getLogger(AdventOfCode2015Day03.class);

    public static int countDistinctHousesOnRound(String input) {

        Round round = new Round();
        round.start();

        char[] inputChars = input.toCharArray();
        for (char c : inputChars) {
            round.nextHouse(c);
        }
        return round.numberOfVisitedDistinctHouses();
    }

    public static void main(String[] args) {
        LOG.info("Starting puzzle...");

        String input = ResourceUtils.readIntoString("aoc_2015_day03_input.txt");
        LOG.info("Distinct number of houses is: " + countDistinctHousesOnRound(input));
    }

    public static class Position {
        private int x = 0;
        private int y = 0;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position nextPosition(char c) {

            if ('^' == c) {
                return new Position(x, y+1);
            }
            else if ('v' == c) {
                return new Position(x, y-1);
            }
            else if ('<' == c) {
                return new Position(x-1, y);
            }
            else if ('>' == c) {
                return new Position(x+1, y);
            }
            else {
                throw new IllegalArgumentException("Unrecognized character: " + c);
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
    }

    public static class House {
        private Position position;
        private int numberOfGits = 0;

        public House(Position position) {
            this.position = position;
        }

        public void dropGift() {
            numberOfGits++;
        }

        public Position getPosition() {
            return position;
        }

        public int getNumberOfGits() {
            return numberOfGits;
        }
    }

    public static class Round {
        private Position positionOfSanta = new Position(0,0);
        private Position positionOfRobot = new Position(0,0);
        private Map<Position, House> houses = Maps.newHashMap();
        private boolean nextMoveIsSanta = true;

        public void start() {
            positionOfSanta = new Position(0,0);
            positionOfRobot = new Position(0,0);
            visitHouseAtPosition(positionOfSanta);
            visitHouseAtPosition(positionOfRobot);
        }

        public void nextHouse(char c) {
            if (nextMoveIsSanta) {
                positionOfSanta = positionOfSanta.nextPosition(c);
                visitHouseAtPosition(positionOfSanta);
            }
            else {
                positionOfRobot = positionOfRobot.nextPosition(c);
                visitHouseAtPosition(positionOfRobot);
            }
            nextMoveIsSanta = !nextMoveIsSanta;
        }

        private void visitHouseAtPosition(Position position) {
            House house = houses.get(position);
            if (house == null) {
                house = new House(position);
                houses.put(position, house);
            }
            house.dropGift();
        }

        public int numberOfVisitedDistinctHouses() {
            return houses.keySet().size();
        }
    }
}
