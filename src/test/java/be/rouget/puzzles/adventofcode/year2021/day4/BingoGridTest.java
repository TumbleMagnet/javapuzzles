package be.rouget.puzzles.adventofcode.year2021.day4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BingoGridTest {

    @Test
    void testParseLine() {
        assertThat(BingoGrid.parseLine(" 9 18 13 17  5")).containsExactly(9, 18, 13, 17, 5);
        assertThat(BingoGrid.parseLine("22 13 17 11  0")).containsExactly(22, 13, 17, 11, 0);
    }

    @Test
    void testGrid() {

        BingoGrid grid = new BingoGrid(List.of(
                "14 21 17 24  4",
                "10 16 15  9 19",
                "18  8 23 26 20",
                "22 11 13  6  5",
                " 2  0 12  3  7"
        ));
        assertThat(grid.addDraw(7)).isFalse();
        assertThat(grid.addDraw(4)).isFalse();
        assertThat(grid.addDraw(9)).isFalse();
        assertThat(grid.addDraw(5)).isFalse();
        assertThat(grid.addDraw(11)).isFalse();
        assertThat(grid.addDraw(17)).isFalse();
        assertThat(grid.addDraw(23)).isFalse();
        assertThat(grid.addDraw(2)).isFalse();
        assertThat(grid.addDraw(0)).isFalse();
        assertThat(grid.addDraw(14)).isFalse();
        assertThat(grid.addDraw(21)).isFalse();
        assertThat(grid.addDraw(24)).isTrue();

        assertThat(grid.getScore()).isEqualTo(4512);
    }
}