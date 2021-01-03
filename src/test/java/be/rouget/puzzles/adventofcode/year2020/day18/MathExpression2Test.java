package be.rouget.puzzles.adventofcode.year2020.day18;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2020.day18.MathExpression2.evaluate;
import static org.assertj.core.api.Assertions.assertThat;

class MathExpression2Test {

    @Test
    void testSimpleExpressions() {
        assertThat(evaluate("1 + 2")).isEqualTo(3L);
        assertThat(evaluate("2 * 3")).isEqualTo(6L);
        assertThat(evaluate("1 + 2 * 3")).isEqualTo(9L);
        assertThat(evaluate("3 * 1 + 2")).isEqualTo(9L);
        assertThat(evaluate("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(231L);
        assertThat(evaluate("2 * 3 + (4 * 5)")).isEqualTo(46L);
        assertThat(evaluate("1 + (2 * 3) + (4 * (5 + 6))")).isEqualTo(51L);
        assertThat(evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(1445L);
        assertThat(evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(669060L);
        assertThat(evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(23340L);
    }

}