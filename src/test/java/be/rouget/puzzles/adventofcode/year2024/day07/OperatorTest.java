package be.rouget.puzzles.adventofcode.year2024.day07;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OperatorTest {

    @Test
    void evaluate() {
        assertThat(Operator.ADD.evaluate(12L, 3L)).isEqualTo(15L);
        assertThat(Operator.MULTIPLY.evaluate(12L, 3L)).isEqualTo(36L);
        assertThat(Operator.CONCATENATE.evaluate(12L, 3L)).isEqualTo(123L);
    }
}