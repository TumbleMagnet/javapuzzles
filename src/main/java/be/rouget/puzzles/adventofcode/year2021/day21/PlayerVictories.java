package be.rouget.puzzles.adventofcode.year2021.day21;

import lombok.Value;

@Value
public class PlayerVictories {
    long player1Victories;
    long player2Victories;

    public PlayerVictories add(PlayerVictories other) {
        return new PlayerVictories(player1Victories + other.getPlayer1Victories(), player2Victories + other.getPlayer2Victories());
    }
}
