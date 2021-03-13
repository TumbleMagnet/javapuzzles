package be.rouget.puzzles.adventofcode.year2015.day17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NoSuchThingAsTooMuchTest {

    @Test
    void testParts() {
        NoSuchThingAsTooMuch noSuchThingAsTooMuch = new NoSuchThingAsTooMuch(25, List.of("20", "15", "10", "5", "5"));
        assertThat(noSuchThingAsTooMuch.computeResultForPart1()).isEqualTo(4);
        assertThat(noSuchThingAsTooMuch.computeResultForPart2()).isEqualTo(3);
    }
}