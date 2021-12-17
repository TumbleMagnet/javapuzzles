package be.rouget.puzzles.adventofcode.year2021.day9;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SmokeBasinTest {

    @Test
    void testSolutions() {

        SmokeBasin smokeBasin = new SmokeBasin(List.of(
                "2199943210",
                "3987894921",
                "9856789892",
                "8767896789",
                "9899965678"
        ));
        assertThat(smokeBasin.computeResultForPart1()).isEqualTo(15L);
    }
}