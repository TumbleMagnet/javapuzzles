package be.rouget.puzzles.adventofcode.year2021.day21;

import lombok.Value;

@Value
public class PlayerState {
    int position;
    int score;

    public PlayerState move(int newPosition) {
        return new PlayerState(newPosition, getScore() + newPosition);
    }
}
