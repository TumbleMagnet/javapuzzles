package be.rouget.puzzles.adventofcode.year2021.day22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RangeTest {

    @Test
    void testIntersects() {

        // Intersecting ranges
        assertThat(r(2, 10).intersects(r(5, 15))).isTrue();
        assertThat(r(2, 10).intersects(r(-5, 5))).isTrue();
        assertThat(r(2, 10).intersects(r(3, 8))).isTrue();
        assertThat(r(2, 10).intersects(r(1, 15))).isTrue();
        assertThat(r(2, 10).intersects(r(2, 10))).isTrue();
        assertThat(r(2, 10).intersects(r(2, 5))).isTrue();
        assertThat(r(2, 10).intersects(r(2, 15))).isTrue();
        assertThat(r(2, 10).intersects(r(2, 2))).isTrue();
        assertThat(r(2, 10).intersects(r(1, 2))).isTrue();
        assertThat(r(2, 10).intersects(r(5, 10))).isTrue();
        assertThat(r(2, 10).intersects(r(10, 12))).isTrue();
        assertThat(r(2, 10).intersects(r(10, 10))).isTrue();
        assertThat(r(2, 10).intersects(r(-5, 10))).isTrue();

        // Non-intersecting ranges
        assertThat(r(2, 10).intersects(r(11, 15))).isFalse();
        assertThat(r(2, 10).intersects(r(0, 1))).isFalse();
        assertThat(r(2, 10).intersects(r(-5, -2))).isFalse();
    }

    @Test
    void testIntersection() {

        Range range = r(2, 10);

        // Non-overlapping ranges
        assertThat(range.intersection(r(15, 20))).isNull();
        assertThat(range.intersection(r(-5, 1))).isNull();

        // Overlapping from the left
        assertThat(range.intersection(r(0, 4))).isEqualTo(r(2, 4));
        assertThat(range.intersection(r(0, 2))).isEqualTo(r(2, 2));

        // Overlapping from the right
        assertThat(range.intersection(r(5, 15))).isEqualTo(r(5, 10));
        assertThat(range.intersection(r(10, 15))).isEqualTo(r(10, 10));

        // Overlapping from middle
        assertThat(range.intersection(r(5, 8))).isEqualTo(r(5, 8));
        assertThat(range.intersection(r(2, 10))).isEqualTo(r(2, 10));
        assertThat(range.intersection(r(1, 11))).isEqualTo(r(2, 10));

        // One element ranges
        assertThat(r(2, 2).intersection(r(2, 2))).isEqualTo(r(2, 2));
        assertThat(r(2, 2).intersection(r(2, 4))).isEqualTo(r(2, 2));
        assertThat(r(2, 2).intersection(r(0, 2))).isEqualTo(r(2, 2));
        assertThat(r(2, 2).intersection(r(0, 4))).isEqualTo(r(2, 2));
        assertThat(r(2, 2).intersection(r(3, 4))).isNull();
        assertThat(r(2, 2).intersection(r(0, 1))).isNull();
    }

    @Test
    void testMinus() {

        Range range = r(2, 10);

        // Non-overlapping ranges
        assertThat(range.minus(r(15, 20))).containsExactlyInAnyOrder(r(2, 10));
        assertThat(range.minus(r(-5, 1))).containsExactlyInAnyOrder(r(2, 10));

        // Overlapping from the left
        assertThat(range.minus(r(0, 4))).containsExactlyInAnyOrder(r(5, 10));
        assertThat(range.minus(r(0, 2))).containsExactlyInAnyOrder(r(3, 10));

        // Overlapping from the right
        assertThat(range.minus(r(5, 15))).containsExactlyInAnyOrder(r(2, 4));
        assertThat(range.minus(r(10, 15))).containsExactlyInAnyOrder(r(2, 9));

        // Overlapping from middle
        assertThat(range.minus(r(5, 8))).containsExactlyInAnyOrder(r(2, 4), r(9, 10));
        assertThat(range.minus(r(2, 10))).isEmpty();
        assertThat(range.minus(r(1, 11))).isEmpty();

        // One element ranges
        assertThat(r(2, 2).minus(r(2, 2))).isEmpty();
        assertThat(r(2, 2).minus(r(2, 4))).isEmpty();
        assertThat(r(2, 2).minus(r(0, 2))).isEmpty();
        assertThat(r(2, 2).minus(r(0, 4))).isEmpty();
    }

    private Range r(int from, int to) {
        return new Range(from, to);
    }
}