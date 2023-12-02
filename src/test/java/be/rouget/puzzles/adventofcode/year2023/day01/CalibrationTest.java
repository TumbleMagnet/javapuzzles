package be.rouget.puzzles.adventofcode.year2023.day01;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day01.Calibration.extractCalibrationPart2;
import static org.assertj.core.api.Assertions.assertThat;

class CalibrationTest {

    @Test
    void testExtractCalibrationPart2() {
        assertThat(extractCalibrationPart2("two1nine")).isEqualTo(29);
        assertThat(extractCalibrationPart2("eightwothree")).isEqualTo(83);
        assertThat(extractCalibrationPart2("abcone2threexyz")).isEqualTo(13);
        assertThat(extractCalibrationPart2("xtwone3four")).isEqualTo(24);
        assertThat(extractCalibrationPart2("4nineeightseven2")).isEqualTo(42);
        assertThat(extractCalibrationPart2("zoneight234")).isEqualTo(14);
        assertThat(extractCalibrationPart2("7pqrstsixteen")).isEqualTo(76);
    }
}