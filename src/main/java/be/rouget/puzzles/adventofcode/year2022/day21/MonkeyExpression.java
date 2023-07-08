package be.rouget.puzzles.adventofcode.year2022.day21;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record MonkeyExpression(String monkeyName, Expression expression) {

    public static MonkeyExpression parse(String input) {
        
        // Pattern 1: "cnjf: 4"
        Pattern constantPattern = Pattern.compile("(.*): (\\d+)");
        Matcher constantMatcher = constantPattern.matcher(input);
        if (constantMatcher.matches()) {
            String monkeyName = constantMatcher.group(1);
            int value = Integer.parseInt(constantMatcher.group(2));
            return new MonkeyExpression(monkeyName, new ConstantExpression(value));
        }

        // Or operationPattern 2: "root: pppw + sjmn"
        Pattern operationPattern = Pattern.compile("(.*): (.*) (.*) (.*)");
        Matcher operationMatcher = operationPattern.matcher(input);
        if (!operationMatcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String monkeyName = operationMatcher.group(1);
        String left = operationMatcher.group(2);
        Operation operation = Operation.fromToken(operationMatcher.group(3));
        String right = operationMatcher.group(4);
        return new MonkeyExpression(monkeyName, new OperationExpression(left, operation, right));
    }
}
