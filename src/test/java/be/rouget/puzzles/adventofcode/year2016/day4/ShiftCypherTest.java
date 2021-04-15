package be.rouget.puzzles.adventofcode.year2016.day4;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day4.ShiftCypher.decrypt;
import static org.assertj.core.api.Assertions.assertThat;

class ShiftCypherTest {

    @Test
    void testDecrypt() {
        // the real name for qzmt-zixmtkozy-ivhz-343 is very encrypted name
        assertThat(decrypt('q', 343)).isEqualTo('v');
        assertThat(decrypt('z', 343)).isEqualTo('e');
    }
}