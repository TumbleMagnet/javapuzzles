package be.rouget.puzzles.adventofcode.year2022.day21;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2022.day21.MonkeyExpression.parse;
import static org.assertj.core.api.Assertions.assertThat;

class MonkeyExpressionTest {

    @Test
    void testParse() {
        assertThat(parse("root: pppw + sjmn")).isEqualTo(new MonkeyExpression("root", new OperationExpression("pppw", Operation.ADD, "sjmn"))); 
        assertThat(parse("root: pppw - sjmn")).isEqualTo(new MonkeyExpression("root", new OperationExpression("pppw", Operation.SUBTRACT, "sjmn"))); 
        assertThat(parse("root: pppw * sjmn")).isEqualTo(new MonkeyExpression("root", new OperationExpression("pppw", Operation.MULTIPLY, "sjmn"))); 
        assertThat(parse("root: pppw / sjmn")).isEqualTo(new MonkeyExpression("root", new OperationExpression("pppw", Operation.DIVIDE, "sjmn"))); 
        assertThat(parse("dbpl: 5")).isEqualTo(new MonkeyExpression("dbpl", new ConstantExpression(5))); 
    }
}