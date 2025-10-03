package be.rouget.puzzles.adventofcode.year2024.day04;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2024.day04.CeresSearch.countXmas;
import static org.assertj.core.api.Assertions.assertThat;


class CeresSearchTest {


    public static final List<String> TEST_INPUT = List.of(
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX"
    );

    @Test
    void testCountXmas() {
        assertThat(countXmas("--XMAS")).isEqualTo(1L);
        assertThat(countXmas("SAMX--")).isEqualTo(1L);
        assertThat(countXmas("SAMXMMSXXMASM")).isEqualTo(2L);
    }

    @Test
    void testPart1() {
        CeresSearch ceresSearch = new CeresSearch(TEST_INPUT);
        assertThat(ceresSearch.computeResultForPart1()).isEqualTo(18L);
    }
}