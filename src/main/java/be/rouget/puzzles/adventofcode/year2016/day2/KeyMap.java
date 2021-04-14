package be.rouget.puzzles.adventofcode.year2016.day2;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;

import java.util.List;
import java.util.Map;

public class KeyMap {

    private final RectangleMap<Keys> map;

    private Position currentPosition;

    public KeyMap(List<String> inputMap) {
        map = new RectangleMap<Keys>(inputMap, Keys::fromMapChar);

        // Start at key 5
        currentPosition = map.getElements().stream()
                .filter(entry -> entry.getValue() == Keys.KEY_5)
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
    }

    public void doMoves(String moves) {
        AocStringUtils.extractCharacterList(moves).stream()
                .map(Move::fromCharacter)
                .forEach(this::doMove);
    }

    public void doMove(Move move) {
        Position newPosition = positionAfterMove(currentPosition, move);
        if (!map.isPositionInMap(newPosition)) {
            return;
        }
        Keys newKey = map.getElementAt(newPosition);
        if (newKey == Keys.EMPTY) {
            return;
        }
        currentPosition = newPosition;
    }

    private static Position positionAfterMove(Position position, Move move) {
        switch (move) {
            // Map coordinates go from top to bottom and from left to right
            case    UP: return new Position(position.getX(), position.getY()-1);
            case  DOWN: return new Position(position.getX(), position.getY()+1);
            case  LEFT: return new Position(position.getX()-1, position.getY());
            case RIGHT: return new Position(position.getX()+1, position.getY());
            default:
                throw new IllegalArgumentException("Illegal move: " + move);
        }
    }

    public Keys getCurrentKey() {
        return map.getElementAt(currentPosition);
    }
}
