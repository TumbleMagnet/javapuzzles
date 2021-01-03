package be.rouget.puzzles.adventofcode.year2020.day18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathExpression2 {

    private static final Pattern ONLY_DIGITS = Pattern.compile("\\d+");
    private static final Pattern PARENTHESIS = Pattern.compile("(.*)(\\([^()]+\\))(.*)");
    private static final Pattern PRODUCT = Pattern.compile("(.+) \\* (.+)");
    private static final Pattern ADDITION = Pattern.compile("(.+) \\+ (.+)");


    public static long evaluate(String expression) {

        // Handle only digits
        Matcher onlyDigits = ONLY_DIGITS.matcher(expression);
        if (onlyDigits.matches()) {
            return Integer.parseInt(onlyDigits.group(0));
        }

        // Handle parenthesis
        Matcher parenthesis = PARENTHESIS.matcher(expression);
        if (parenthesis.matches()) {
            long resultOfParenthesis = evaluate(parenthesis.group(2).replace("(", "").replace(")", ""));
            return evaluate(parenthesis.group(1) + resultOfParenthesis + parenthesis.group(3));
        }

        // Handle product
        Matcher product = PRODUCT.matcher(expression);
        if (product.matches()) {
            return evaluate(product.group(1)) * evaluate(product.group(2));
        }

        // Handle addition
        Matcher addition = ADDITION.matcher(expression);
        if (addition.matches()) {
            return evaluate(addition.group(1)) + evaluate(addition.group(2));
        }

        throw new UnsupportedOperationException("Could not handle expression " + expression);
    }
}
