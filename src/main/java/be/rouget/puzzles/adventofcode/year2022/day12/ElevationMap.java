package be.rouget.puzzles.adventofcode.year2022.day12;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.List;
import java.util.Map;

@SuppressWarnings("java:S2160") // Ok to not override equals
public class ElevationMap extends RectangleMap<MapHeight> {

    private final Position start;
    private final Position end;
    
    public  ElevationMap(List<String> input) {
        super(input, MapHeight::new);

        start = getElements().stream()
                .filter(e -> e.getValue().isStart())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();

        end = getElements().stream()
                .filter(e -> e.getValue().isEnd())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @SuppressWarnings("java:S2234") // Ok to have method calls with parameters seemingly in reversed order
    public boolean isValidMove(Position from, Position to, MoveDirection moveDirection) {
        if (MoveDirection.FORWARD == moveDirection) {
            return isValidMoveForward(from, to);
        } else {
            return isValidMoveForward(to, from);
        }
    }

    private boolean isValidMoveForward(Position from, Position to) {
        return getElementAt(to).getElevation() <= getElementAt(from).getElevation() + 1;
    }
}
