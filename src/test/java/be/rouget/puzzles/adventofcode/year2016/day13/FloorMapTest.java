package be.rouget.puzzles.adventofcode.year2016.day13;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FloorMapTest {

    @Test
    void testMapSquares() {
        FloorMap floorMap = new FloorMap(10);
        RectangleMap<MapSquare> actualCorner = floorMap.exportFromTopLeft(10, 7);
        RectangleMap<MapSquare> expectedCorner = new RectangleMap<>(List.of(
                ".#.####.##",
                "..#..#...#",
                "#....##...",
                "###.#.###.",
                ".##..#..#.",
                "..##....#.",
                "#...##.###"
        ), MapSquare::fromMapChar);
        assertThat(actualCorner).isEqualTo(expectedCorner);
    }

    @Test
    void testShortestPath() {
        FloorMap floorMap = new FloorMap(10);
        int length = floorMap.lengthOfShortestPath(new Position(1, 1), new Position(7, 4));
        assertThat(length).isEqualTo(11);
    }

}