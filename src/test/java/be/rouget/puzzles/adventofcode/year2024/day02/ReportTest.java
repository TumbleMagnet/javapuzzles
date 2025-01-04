package be.rouget.puzzles.adventofcode.year2024.day02;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2024.day02.Report.parse;
import static org.assertj.core.api.Assertions.assertThat;

class ReportTest {

    @Test
    void isSafe() {
        assertThat(parse("7 6 4 2 1").isSafe()).isTrue();
        assertThat(parse("1 2 7 8 9").isSafe()).isFalse();
        assertThat(parse("9 7 6 2 1").isSafe()).isFalse();
        assertThat(parse("1 3 2 4 5").isSafe()).isFalse();
        assertThat(parse("8 6 4 4 1").isSafe()).isFalse();
        assertThat(parse("1 3 6 7 9").isSafe()).isTrue();
    }

    @Test
    void isSafeWithDampener() {
        assertThat(parse("7 6 4 2 1").isSafeWithDampener()).isTrue();
        assertThat(parse("1 2 7 8 9").isSafeWithDampener()).isFalse();
        assertThat(parse("9 7 6 2 1").isSafeWithDampener()).isFalse();
        assertThat(parse("1 3 2 4 5").isSafeWithDampener()).isTrue();
        assertThat(parse("8 6 4 4 1").isSafeWithDampener()).isTrue();
        assertThat(parse("1 3 6 7 9").isSafeWithDampener()).isTrue();
    }

}