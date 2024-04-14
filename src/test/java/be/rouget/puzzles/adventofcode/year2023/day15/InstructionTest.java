package be.rouget.puzzles.adventofcode.year2023.day15;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionTest {

    @Test
    @SuppressWarnings("java:S5838") // OK to hat isEqualTo(0) here
    void testLabelHash() {
        assertThat(new AddLens("rn", 1).computeLabelHash()).isEqualTo(0);
        assertThat(new RemoveLens("cm").computeLabelHash()).isEqualTo(0);
        assertThat(new AddLens("qp", 3).computeLabelHash()).isEqualTo(1);
        assertThat(new AddLens("cm", 2).computeLabelHash()).isEqualTo(0);
        assertThat(new RemoveLens("qp").computeLabelHash()).isEqualTo(1);
        assertThat(new AddLens("pc", 4).computeLabelHash()).isEqualTo(3);
        assertThat(new AddLens("ot", 9).computeLabelHash()).isEqualTo(3);
        assertThat(new AddLens("ab", 5).computeLabelHash()).isEqualTo(3);
        assertThat(new RemoveLens("pc").computeLabelHash()).isEqualTo(3);
        assertThat(new AddLens("pc", 6).computeLabelHash()).isEqualTo(3);
        assertThat(new AddLens("ot", 7).computeLabelHash()).isEqualTo(3);
    }
}