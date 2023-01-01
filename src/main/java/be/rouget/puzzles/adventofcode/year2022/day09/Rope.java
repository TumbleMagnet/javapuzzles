package be.rouget.puzzles.adventofcode.year2022.day09;

import be.rouget.puzzles.adventofcode.util.map.Direction;
import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public record Rope(List<Position> knots) {

    public static Rope buidRope(int numberOfKnots) {
        List<Position> knots = IntStream.rangeClosed(1, numberOfKnots)
                .mapToObj(i -> new Position(0, 0))
                .toList();
        return new Rope(knots);
    }
    
    public Rope moveHead(Direction direction) {
        List<Position> newKnots = Lists.newArrayList();
        for (int i = 0; i < knots().size(); i++) {
            Position current = knots.get(0);
            if (i == 0) {
                // Move head to follow direction
                newKnots.add(current.getNeighbour(direction));
            } else {
                // Move following knots to catch up with the previous one
                newKnots.add(moveTail(newKnots.get(i-1), knots.get(i)));
            }
        }
        return new Rope(newKnots);
    }
    
    public Position tail() {
        return knots.get(knots().size() - 1);
    }

    public static Position moveTail(Position head, Position tail) {
        int deltaX = head.getX() - tail.getX();
        int deltaY = head.getY() - tail.getY();
        if (abs(deltaX) <= 1 && abs(deltaY) <= 1) {
            return tail;
        }
        return new Position(tail.getX() + Integer.signum(deltaX), tail.getY() + Integer.signum(deltaY));
    }
}
