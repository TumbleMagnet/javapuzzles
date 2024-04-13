package be.rouget.puzzles.adventofcode.year2019;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day24.Grid;
import static org.assertj.core.api.Assertions.*;

class AoC2019Day24Test {
    
    @Test
    void testComputeBioDiversityRating() {
        assertThat(new Grid(Lists.newArrayList(".....", ".....", ".....", ".....", ".....")).computeBioDiversityRating()).isEqualTo(0);
        assertThat(new Grid(Lists.newArrayList("#....", ".....", ".....", ".....", ".....")).computeBioDiversityRating()).isEqualTo(1);
        assertThat(new Grid(Lists.newArrayList(".#...", ".....", ".....", ".....", ".....")).computeBioDiversityRating()).isEqualTo(2);
        assertThat(new Grid(Lists.newArrayList("##...", ".....", ".....", ".....", ".....")).computeBioDiversityRating()).isEqualTo(3);
        assertThat(new Grid(Lists.newArrayList(".....", ".....", ".....", "#....", ".#...")).computeBioDiversityRating()).isEqualTo(2129920);
    }

    @Test
    void testNextGrid() {
        Grid grid = new Grid(Lists.newArrayList(
                "....#",
                "#..#.",
                "#..##",
                "..#..",
                "#...."));
        Grid expected = new Grid(Lists.newArrayList(
                "#..#.",
                "####.",
                "###.#",
                "##.##",
                ".##.."));

        Grid actual = grid.nextGrid();
        actual.print();
        assertThat(actual.computeBioDiversityRating()).isEqualTo(expected.computeBioDiversityRating());
    }

}