package be.rouget.puzzles.adventofcode.year2015.day20;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day20.InfiniteElvesAndInfiniteHouses.findDivisors;
import static org.assertj.core.api.Assertions.assertThat;

class InfiniteElvesAndInfiniteHousesTest {

    @Test
    void testFindDivisors() {
        assertThat(findDivisors(1)).containsExactlyInAnyOrder(1);
        assertThat(findDivisors(2)).containsExactlyInAnyOrder(1, 2);
        assertThat(findDivisors(3)).containsExactlyInAnyOrder(1, 3);
        assertThat(findDivisors(4)).containsExactlyInAnyOrder(1, 2, 4);
        assertThat(findDivisors(50)).containsExactlyInAnyOrder(1, 2, 5, 10, 25, 50);
        assertThat(findDivisors(125)).containsExactlyInAnyOrder(1, 5, 25, 125);
        assertThat(findDivisors(316)).containsExactlyInAnyOrder(1, 2, 4, 79, 158, 316);
    }
}