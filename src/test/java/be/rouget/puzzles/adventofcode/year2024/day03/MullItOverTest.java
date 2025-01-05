package be.rouget.puzzles.adventofcode.year2024.day03;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2024.day03.MullItOver.*;
import static org.assertj.core.api.Assertions.assertThat;

class MullItOverTest {

    @Test
    void testExecuteLine() {
        assertThat(executeLine("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")).isEqualTo(161L);
        assertThat(executeLineWithConditions("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")).isEqualTo(48L);
    }

    @Test
    void testEvaluateOperation() {
        assertThat(evaluateOperation("mul(44,46)")).isEqualTo(2024L);
        assertThat(evaluateOperation("mul(123,4)")).isEqualTo(492L);
    }

}