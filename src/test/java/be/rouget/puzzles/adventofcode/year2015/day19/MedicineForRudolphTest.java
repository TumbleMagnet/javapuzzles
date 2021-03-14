package be.rouget.puzzles.adventofcode.year2015.day19;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day19.MedicineForRudolph.replaceAtIndex;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MedicineForRudolphTest {

    @Test
    void testReplaceAtIndex() {
        assertThat(replaceAtIndex("ABCDEF", new Replacement("CD", "XYZ"), 2)).isEqualTo("ABXYZEF");
        assertThat(replaceAtIndex("ABCDEF", new Replacement("ABC", "XYZ"), 0)).isEqualTo("XYZDEF");
    }
}