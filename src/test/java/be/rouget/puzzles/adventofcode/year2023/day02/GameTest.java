package be.rouget.puzzles.adventofcode.year2023.day02;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2023.day02.Game.parseGame;
import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void testParseGame() {
        assertThat(parseGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))
                .isEqualTo(new Game(1, List.of(
                        new Cubes(4, 0, 3),
                        new Cubes(1, 2, 6),
                        new Cubes(0, 2, 0)
                )));
    }
}