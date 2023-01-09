package be.rouget.puzzles.adventofcode.year2022.day13;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PacketPairTest {

    @Test
    void testInInRightOrder() {
        assertThat(isInRightOrder("[1,1,3,1,1]", "[1,1,5,1,1]")).isTrue();
        assertThat(isInRightOrder("[[1],[2,3,4]]", "[[1],4]")).isTrue();
        assertThat(isInRightOrder("[9]", "[[8,7,6]]")).isFalse();
        assertThat(isInRightOrder("[[4,4],4,4]", "[[4,4],4,4,4]")).isTrue();
        assertThat(isInRightOrder("[7,7,7,7]", "[7,7,7]")).isFalse();
        assertThat(isInRightOrder("[]", "[3]")).isTrue();
        assertThat(isInRightOrder("[[[]]]", "[[]]")).isFalse();
        assertThat(isInRightOrder("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]")).isFalse();
    }

    private boolean isInRightOrder(String first, String second) {
        return new PacketPair(0, first, second).isInRightOrder();
    }
}