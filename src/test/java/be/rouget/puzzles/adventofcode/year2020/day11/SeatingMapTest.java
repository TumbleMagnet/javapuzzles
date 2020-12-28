package be.rouget.puzzles.adventofcode.year2020.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SeatingMapTest {

    public static final List<String> MAP_0 = List.of(
            "L.LL.LL.LL",
            "LLLLLLL.LL",
            "L.L.L..L..",
            "LLLL.LL.LL",
            "L.LL.LL.LL",
            "L.LLLLL.LL",
            "..L.L.....",
            "LLLLLLLLLL",
            "L.LLLLLL.L",
            "L.LLLLL.LL"
    );
    public static final List<String> MAP_1 = List.of(
            "#.##.##.##",
            "#######.##",
            "#.#.#..#..",
            "####.##.##",
            "#.##.##.##",
            "#.#####.##",
            "..#.#.....",
            "##########",
            "#.######.#",
            "#.#####.##"
    );
    public static final List<String> MAP_2 = List.of(
            "#.LL.L#.##",
            "#LLLLLL.L#",
            "L.L.L..L..",
            "#LLL.LL.L#",
            "#.LL.LL.LL",
            "#.LLLL#.##",
            "..L.L.....",
            "#LLLLLLLL#",
            "#.LLLLLL.L",
            "#.#LLLL.##"
    );

    @Test
    void testEquals() {
        assertThat(new SeatingMap(MAP_0)).isEqualTo(new SeatingMap(MAP_0));
        assertThat(new SeatingMap(MAP_1)).isEqualTo(new SeatingMap(MAP_1));
        assertThat(new SeatingMap(MAP_0)).isNotEqualTo(new SeatingMap(MAP_1));
    }

    @Test
    void testNextMap() {
        assertThat(new SeatingMap(MAP_0).predictNextMap()).isEqualTo(new SeatingMap(MAP_1));
        assertThat(new SeatingMap(MAP_1).predictNextMap()).isEqualTo(new SeatingMap(MAP_2));
    }

    @Test
    void testCountAdjacentOccupiedSeats() {
        SeatingMap seatingMap = new SeatingMap(MAP_1);
        assertThat(seatingMap.countAdjacentOccupiedSeats(new Position(3, 0))).isEqualTo(4);
    }
}