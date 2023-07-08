package be.rouget.puzzles.adventofcode.year2022.day21;

import java.util.Map;

public class Expressions {

    private static Map<String, Expression> expressionsByName;

    private Expressions() {
    }

    public static void initialize(Map<String, Expression> expressions) {
        expressionsByName = expressions;
    }

    public static Expression get(String monkeyName) {
        Expression expression = expressionsByName.get(monkeyName);
        if (expression == null) {
            throw new IllegalArgumentException("Cannot find expression for monkey " + monkeyName);
        }
        return expression;
    }

    public static void updateExpression(String monkeyName, Expression newExpression) {
        expressionsByName.put(monkeyName, newExpression);
    }
}
