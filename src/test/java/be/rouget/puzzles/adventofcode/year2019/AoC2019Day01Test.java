package be.rouget.puzzles.adventofcode.year2019;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day01.fuelForModule;
import static org.assertj.core.api.Assertions.assertThat;

class AoC2019Day01Test {

    @Test
    void testFuelForModule() {
        assertThat(fuelForModule(12L)).isEqualTo(2L);
        assertThat(fuelForModule(14L)).isEqualTo(2L);
        assertThat(fuelForModule(1969L)).isEqualTo(966L);
        assertThat(fuelForModule(100756L)).isEqualTo(50346L);
    }
}