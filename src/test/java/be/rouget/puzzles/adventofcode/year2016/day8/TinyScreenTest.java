package be.rouget.puzzles.adventofcode.year2016.day8;

import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TinyScreenTest {

    private static final Logger LOG = LogManager.getLogger(TinyScreenTest.class);

    @Test
    void testRectangleCommand() {
        TinyScreen screen = new TinyScreen(7, 3);
        screen.apply(new RectangleCommand(3, 2));
        assertScreenIs(screen, List.of(
                        "###....",
                        "###....",
                        "......."
        ));
        screen.apply(new RotateColumnCommand(1, 1));
        assertScreenIs(screen, List.of(
                "#.#....",
                "###....",
                ".#....."
        ));
        screen.apply(new RotateRowCommand(0, 4));
        assertScreenIs(screen, List.of(
                "....#.#",
                "###....",
                ".#....."
        ));
        screen.apply(new RotateColumnCommand(1, 1));
        assertScreenIs(screen, List.of(
                ".#..#.#",
                "#.#....",
                ".#....."
        ));
    }

    private void assertScreenIs(TinyScreen screen, List<String> lines) {
        assertThat(screen.getDisplay()).isEqualTo(
                new RectangleMap<ScreenPixel>(lines, ScreenPixel::fromMapChar)
        );
    }
}