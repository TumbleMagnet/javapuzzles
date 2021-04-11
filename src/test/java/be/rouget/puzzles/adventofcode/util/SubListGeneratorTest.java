package be.rouget.puzzles.adventofcode.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.util.SubListGenerator.subListsOfSize;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

class SubListGeneratorTest {

    @Test
    void testSubLists() {
        List<Integer> list = of(1, 2, 3, 4);
        assertThat(subListsOfSize(list, 1)).containsExactlyInAnyOrder(of(1), of(2), of(3), of(4));
        assertThat(subListsOfSize(list, 2)).containsExactlyInAnyOrder(of(1,2), of(1,3), of(1,4), of(2,3), of(2,4), of(3,4));
        assertThat(subListsOfSize(list, 3)).containsExactlyInAnyOrder(of(1,2,3), of(1,2,4), of(1,3,4), of(2,3,4));
        assertThat(subListsOfSize(list, 4)).containsExactlyInAnyOrder(of(1,2,3,4));
    }
}