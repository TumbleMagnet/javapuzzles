package be.rouget.puzzles.adventofcode.year2020.day20;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {

    private ImageTile tile1951 = new ImageTile(1951, List.of(
            "#...##.#..",
            "..#.#..#.#",
            ".###....#.",
            "###.##.##.",
            ".###.#####",
            ".##.#....#",
            "#...######",
            ".....#..##",
            "#.####...#",
            "#.##...##."
    ));

    private ImageTile tile2311 = new ImageTile(2311, List.of(
            "..###..###",
            "###...#.#.",
            "..#....#..",
            ".#.#.#..##",
            "##...#.###",
            "##.##.###.",
            "####.#...#",
            "#...##..#.",
            "##..#.....",
            "..##.#..#."
    ));

    private ImageTile tile2729 = new ImageTile(2729, List.of(
            "#.##...##.",
            "##..#.##..",
            "##.####...",
            "####.#.#..",
            ".#.####...",
            ".##..##.#.",
            "....#..#.#",
            "..#.#.....",
            "####.#....",
            "...#.#.#.#"
    ));

    @Test
    void matchesHorizontally() {
        assertThat(TiledImage.matchesHorizontally(tile1951, tile2311)).isEqualTo(true);
        assertThat(TiledImage.matchesHorizontally(tile2311, tile1951)).isEqualTo(false);
    }

    @Test
    void matchesVertically() {
        assertThat(TiledImage.matchesVertically(tile1951, tile2729)).isEqualTo(true);
        assertThat(TiledImage.matchesVertically(tile1951, tile2311)).isEqualTo(false);
    }
}