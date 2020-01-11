package be.rouget.puzzles.adventofcode.year2019;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day25.Item.*;
import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day25.Item;

public class AoC2019Day25Test {

    @Test
    public void testCombinationList() {
        testCombination(0);
        testCombination(1, JAM);
        testCombination(2, LOOM);
        testCombination(3, LOOM, JAM);
        testCombination(4, MUG);
        testCombination(5, MUG, JAM);
        testCombination(6, MUG, LOOM);
        testCombination(7, MUG, LOOM, JAM);
        testCombination(8, SPOOL_OF_CAT6);
        testCombination(16, PRIME_NUMBER);
        testCombination(32, FOOD_RATION);
        testCombination(64, FUEL_CELL);
        testCombination(128, MANIFOLD);
        testCombination(255, MANIFOLD, FUEL_CELL, FOOD_RATION, PRIME_NUMBER, SPOOL_OF_CAT6, MUG, LOOM, JAM);

    }

    private void testCombination(int index, Item... items) {
        Assertions.assertThat(Item.fromCombinationIndex(index)).containsExactlyInAnyOrder(items);
    }
}