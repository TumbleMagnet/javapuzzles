package be.rouget.puzzles.adventofcode.year2016.day7;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day7.IPV7.supportsSSL;
import static be.rouget.puzzles.adventofcode.year2016.day7.IPV7.supportsTLS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IPV7Test {

    @Test
    void testSupportsTLS() {
        assertThat(supportsTLS("abba[mnop]qrst")).isTrue();
        assertThat(supportsTLS("abcd[bddb]xyyx")).isFalse();
        assertThat(supportsTLS("aaaa[qwer]tyui")).isFalse();
        assertThat(supportsTLS("ioxxoj[asdfgh]zxcvbn")).isTrue();
    }

    @Test
    void testSupportsSSL() {
        assertThat(supportsSSL("aba[bab]xyz")).isTrue();
        assertThat(supportsSSL("xyx[xyx]xyx")).isFalse();
        assertThat(supportsSSL("aaa[kek]eke")).isTrue();
        assertThat(supportsSSL("aaa[kek]eke")).isTrue();
    }
}