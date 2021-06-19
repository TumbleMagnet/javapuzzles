package be.rouget.puzzles.adventofcode.year2016.day9;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day9.ExplosivesInCyberspace.decompress;
import static be.rouget.puzzles.adventofcode.year2016.day9.ExplosivesInCyberspace.lengthAfterDecompression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExplosivesInCyberspaceTest {

    @Test
    void testDecompress() {
        assertThat(decompress("ADVENT")).isEqualTo("ADVENT");
        assertThat(decompress("A(1x5)BC")).isEqualTo("ABBBBBC");
        assertThat(decompress("(3x3)XYZ")).isEqualTo("XYZXYZXYZ");
        assertThat(decompress("A(2x2)BCD(2x2)EFG")).isEqualTo("ABCBCDEFEFG");
        assertThat(decompress("(6x1)(1x3)A")).isEqualTo("(1x3)A");
        assertThat(decompress("X(8x2)(3x3)ABCY")).isEqualTo("X(3x3)ABC(3x3)ABCY");
    }

    @Test
    void testLengthAfterDecompression() {
        assertThat(lengthAfterDecompression("ADVENT")).isEqualTo(6L);
        assertThat(lengthAfterDecompression("(3x3)XYZ")).isEqualTo(9L);
        assertThat(lengthAfterDecompression("(3x3)XYZA")).isEqualTo(10L);
        assertThat(lengthAfterDecompression("X(8x2)(3x3)ABCY")).isEqualTo(20L);
        assertThat(lengthAfterDecompression("(27x12)(20x12)(13x14)(7x10)(1x12)A")).isEqualTo(241920L);
        assertThat(lengthAfterDecompression("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")).isEqualTo(445L);
    }
}