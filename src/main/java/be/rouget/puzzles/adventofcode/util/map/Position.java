package be.rouget.puzzles.adventofcode.util.map;

import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;

@Value
public class Position {
    int x;
    int y;

    public List<Position> enumerateNeighbours() {
        List<Position> neighbours = Lists.newArrayList();
        for (int i = x-1; i < x+2; i++) {
            for (int j = y-1; j < y+2; j++) {
                if (i != x || j != y) {
                    neighbours.add(new Position(i, j));
                }
            }
        }
        return neighbours;
    }

    public int computeManhattanDistance(Position another) {
        return Math.abs(this.getX() - another.getX())
                + Math.abs(this.getY() - another.getY());
    }
}
