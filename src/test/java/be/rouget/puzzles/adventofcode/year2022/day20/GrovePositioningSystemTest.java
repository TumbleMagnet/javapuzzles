package be.rouget.puzzles.adventofcode.year2022.day20;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static be.rouget.puzzles.adventofcode.year2022.day20.GrovePositioningSystem.mix;
import static org.assertj.core.api.Assertions.assertThat;

class GrovePositioningSystemTest {

    @Test
    void testMix() {
        assertThat(mix(List.of(1L, 2L, -3L, 3L, -2L, 0L, 4L)))
                // .containsExactly(1, 2, -3, 4, 0, 3, -2); <= outcome in sample
                .containsExactly(-2L, 1L, 2L, -3L, 4L, 0L, 3L); // actual outcome (equivalent since it can be considered as a "ring")
    }
    
    @Test
    void testMixValueWithIndex() {

        List<IndexedValue> startValues = List.of(
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(-3, 2),
                new IndexedValue(3, 3),
                new IndexedValue(-2, 4),
                new IndexedValue(0, 5),
                new IndexedValue(4, 6)
        );

        ArrayList<IndexedValue> list1 = Lists.newArrayList(startValues);
        GrovePositioningSystem.mixValueWithIndex(list1, 0);
        assertThat(list1).containsExactly(
                new IndexedValue(2, 1),
                new IndexedValue(1, 0),
                new IndexedValue(-3, 2),
                new IndexedValue(3, 3),
                new IndexedValue(-2, 4),
                new IndexedValue(0, 5),
                new IndexedValue(4, 6)
        );

        GrovePositioningSystem.mixValueWithIndex(list1, 1);
        assertThat(list1).containsExactly(
                new IndexedValue(1, 0),
                new IndexedValue(-3, 2),
                new IndexedValue(2, 1),
                new IndexedValue(3, 3),
                new IndexedValue(-2, 4),
                new IndexedValue(0, 5),
                new IndexedValue(4, 6)
        );

        GrovePositioningSystem.mixValueWithIndex(list1, 2);
        assertThat(list1).containsExactly(
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(3, 3),
                new IndexedValue(-2, 4),
                new IndexedValue(-3, 2),
                new IndexedValue(0, 5),
                new IndexedValue(4, 6)
        );

        GrovePositioningSystem.mixValueWithIndex(list1, 3);
        assertThat(list1).containsExactly(
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(-2, 4),
                new IndexedValue(-3, 2),
                new IndexedValue(0, 5),
                new IndexedValue(3, 3),
                new IndexedValue(4, 6)
        );

        // -2 moves between 4 and 1:
        // 1, 2, -3, 0, 3, 4, -2
        // (or equivalent: -2, 1, 2, -3, 0, 3, 4)
        GrovePositioningSystem.mixValueWithIndex(list1, 4);
        assertThat(list1).containsExactly(
                new IndexedValue(-2, 4),
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(-3, 2),
                new IndexedValue(0, 5),
                new IndexedValue(3, 3),
                new IndexedValue(4, 6)
        );

        // -2, 1, 2, -3, 0, 3, 4
        // 0 does not move:
        // -2, 1, 2, -3, 0, 3, 4
        GrovePositioningSystem.mixValueWithIndex(list1, 5);
        assertThat(list1).containsExactly(
                new IndexedValue(-2, 4),
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(-3, 2),
                new IndexedValue(0, 5),
                new IndexedValue(3, 3),
                new IndexedValue(4, 6)
        );


        // -2, 1, 2, -3, 0, 3, 4
        // 4 moves between -3 and 0:
        // -2, 1, 2, -3, 4, 0, 3        
        GrovePositioningSystem.mixValueWithIndex(list1, 6);
        assertThat(list1).containsExactly(
                new IndexedValue(-2, 4),
                new IndexedValue(1, 0),
                new IndexedValue(2, 1),
                new IndexedValue(-3, 2),
                new IndexedValue(4, 6),
                new IndexedValue(0, 5),
                new IndexedValue(3, 3)
        );
    }
}