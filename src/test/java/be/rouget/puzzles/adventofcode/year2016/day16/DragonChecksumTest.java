package be.rouget.puzzles.adventofcode.year2016.day16;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day16.DragonChecksum.computeChecksum;
import static be.rouget.puzzles.adventofcode.year2016.day16.DragonChecksum.generateWithDragonCurve;
import static org.assertj.core.api.Assertions.assertThat;

class DragonChecksumTest {

    @Test
    void testGenerateWithDragonCurve() {
        assertThat(generateWithDragonCurve("1")).isEqualTo("100");
        assertThat(generateWithDragonCurve("0")).isEqualTo("001");
        assertThat(generateWithDragonCurve("11111")).isEqualTo("11111000000");
        assertThat(generateWithDragonCurve("111100001010")).isEqualTo("1111000010100101011110000");
    }

    @Test
    void testChecksum() {
        assertThat(computeChecksum("110101")).isEqualTo("100");
        assertThat(computeChecksum("110010110100")).isEqualTo("100");
        assertThat(computeChecksum("0111110101")).isEqualTo("01100");
        assertThat(computeChecksum("10000011110010000111")).isEqualTo("01100");
    }
}