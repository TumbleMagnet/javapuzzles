package be.rouget.puzzles.adventofcode.year2020.day17;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.ImageProducer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConwayCubesTest {

    @Test
    void computeResultForPart1() {

        ConwayCubes cubes = new ConwayCubes(List.of(
                ".#.",
                "..#",
                "###"
        ));
        assertThat(cubes.computeResultForPart1()).isEqualTo(112);
    }
}