package be.rouget.puzzles.adventofcode.year2023.day15;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day15.Hash.compute;
import static org.assertj.core.api.Assertions.assertThat;

class HashTest {

    @Test
    void testHashes() {
        assertThat(compute("HASH")).isEqualTo(52);
        assertThat(compute("rn=1")).isEqualTo(30);
        assertThat(compute("cm-")).isEqualTo(253);
        assertThat(compute("qp=3")).isEqualTo(97);
        assertThat(compute("cm=2")).isEqualTo(47);
        assertThat(compute("qp-")).isEqualTo(14);
        assertThat(compute("pc=4")).isEqualTo(180);
        assertThat(compute("ot=9")).isEqualTo(9);
        assertThat(compute("ab=5")).isEqualTo(197);
        assertThat(compute("pc-")).isEqualTo(48);
        assertThat(compute("pc=6")).isEqualTo(214);
        assertThat(compute("ot=7")).isEqualTo(231);
    }

}