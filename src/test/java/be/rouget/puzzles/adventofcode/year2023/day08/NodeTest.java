package be.rouget.puzzles.adventofcode.year2023.day08;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    @Test
    void testParse() {
        assertThat(Node.parse("DRM = (DLQ, BGR)")).isEqualTo(new Node("DRM", "DLQ", "BGR"));
    }
}