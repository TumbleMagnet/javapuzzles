package be.rouget.puzzles.adventofcode.year2022.day11;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monkey {
    public static final String CANNOT_PARSE_INPUT = "Cannot parse input: ";
    private final int index;
    private final Deque<Long> items;
    private final UnaryOperator<Long> operation;
    private final long testDivider;
    private final int destinationIndexIfTrue;
    private final int destinationIndexIfFalse;
    
    private long numberOfItems = 0;
    private boolean inspectionDividesWorryLevel = true;
    private long commonModulo;

    public Monkey(int index, List<Long> items, UnaryOperator<Long> operation, long testDivider, int destinationIndexIfTrue, int destinationIndexIfFalse) {
        this.index = index;
        this.items = new ArrayDeque<>(items);
        this.operation = operation;
        this.testDivider = testDivider;
        this.destinationIndexIfTrue = destinationIndexIfTrue;
        this.destinationIndexIfFalse = destinationIndexIfFalse;
    }

    public Optional<ThrownItem> throwNextItem() {
        if (items.isEmpty()) {
            return Optional.empty();
        }
        numberOfItems++;
        Long item = items.removeFirst();
        item = operation.apply(item) % commonModulo;
        if (inspectionDividesWorryLevel) {
            item = item / 3;
        }
        int destination = item % testDivider == 0 ? destinationIndexIfTrue : destinationIndexIfFalse;
        return Optional.of(new ThrownItem(destination, item));
    }

    public void receiveItem(long worryLevel) {
        items.addLast(worryLevel);
    }

    public static Monkey parse(List<String> input) {
        if (input.size() != 6) {
            throw new IllegalArgumentException("Expected input of size 6, got: " + input.size());
        }
        //  Test: divisible by 19
        //    If true: throw to monkey 5
        //    If false: throw to monkey 6

        int index = extractMonkeyIndex(input.get(0));
        List<Long> items = extractItems(input.get(1));
        UnaryOperator<Long> operation = extractOperation(input.get(2));
        long testDivider = extractTestDivider(input.get(3));
        int destinationIndexIfTrue = extractDestinationIfTrue(input.get(4));
        int destinationIndexIfFalse = extractDestinationIfFalse(input.get(5));
        return new Monkey(index, items, operation, testDivider, destinationIndexIfTrue, destinationIndexIfFalse);
    }

    private static int extractMonkeyIndex(String input) {
        Pattern pattern = Pattern.compile("Monkey (\\d+):");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private static List<Long> extractItems(String input) {
        Pattern pattern = Pattern.compile(" {2}Starting items: (.*)$");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        String itemString = matcher.group(1);
        String[] tokens = itemString.split(", ");
        return Arrays.stream(tokens)
                .map(Long::parseLong)
                .toList();
    }

    private static UnaryOperator<Long> extractOperation(String input) {
        Pattern pattern = Pattern.compile(" {2}Operation: new = old (.) (.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        String operator = matcher.group(1);
        String operand = matcher.group(2);

        // Special case for "old * old"
        if ("*".equals(operator) && "old".equals(operand)) {
            return level -> level * level;
        }

        long value = Integer.parseInt(operand);
        return switch (operator) {
            case "+" -> level -> level + value;
            case "*" -> level -> level * value;
            default  -> throw new IllegalStateException("Invalid operator: " + operator);
        };
    }

    private static long extractTestDivider(String input) {
        Pattern pattern = Pattern.compile(" {2}Test: divisible by (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        return Long.parseLong(matcher.group(1));
    }

    private static int extractDestinationIfTrue(String input) {
        Pattern pattern = Pattern.compile(" {4}If true: throw to monkey (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private static int extractDestinationIfFalse(String input) {
        Pattern pattern = Pattern.compile(" {4}If false: throw to monkey (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(CANNOT_PARSE_INPUT + input);
        }
        return Integer.parseInt(matcher.group(1));
    }

    public int getIndex() {
        return index;
    }

    public long getNumberOfItems() {
        return numberOfItems;
    }

    public void setInspectionDividesWorryLevel(boolean inspectionDividesWorryLevel) {
        this.inspectionDividesWorryLevel = inspectionDividesWorryLevel;
    }

    public long getTestDivider() {
        return testDivider;
    }

    public void setCommonModulo(long commonModulo) {
        this.commonModulo = commonModulo;
    }
}
