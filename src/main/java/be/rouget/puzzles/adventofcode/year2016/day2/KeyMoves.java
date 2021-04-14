package be.rouget.puzzles.adventofcode.year2016.day2;

import lombok.Value;

@Value
class KeyMoves {

    Key mainKey;
    Key up;
    Key left;
    Key right;
    Key down;

    public Key doMove(Move move) {
        switch (move) {
            case UP:
                return up;
            case DOWN:
                return down;
            case LEFT:
                return left;
            case RIGHT:
                return right;
            default:
                throw new IllegalArgumentException("Invalid move: " + move);
        }
    }
}
