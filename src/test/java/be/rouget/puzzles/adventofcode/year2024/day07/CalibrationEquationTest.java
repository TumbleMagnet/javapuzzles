package be.rouget.puzzles.adventofcode.year2024.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalibrationEquationTest {

    @Test
    void parse() {
        assertThat(CalibrationEquation.parse("10274: 2 923 7 658 40 4 70"))
                .isEqualTo(new CalibrationEquation(10274L, List.of(2L, 923L, 7L, 658L, 40L, 4L, 70L)));
    }
}