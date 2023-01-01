package be.rouget.puzzles.adventofcode.year2022.day08;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TreeMapTest {

    @Test
    void testExample() {
        List<String> input = List.of(
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        );
        TreeMap treeMap = new TreeMap(input);
        assertThat(treeMap.countVisibleTrees()).isEqualTo(21);
        assertThat(treeMap.highestScenicScore()).isEqualTo(8);
    }
}