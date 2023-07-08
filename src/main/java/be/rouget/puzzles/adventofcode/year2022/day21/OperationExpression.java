package be.rouget.puzzles.adventofcode.year2022.day21;

import java.util.Optional;

public record OperationExpression(String left, Operation operation, String right) implements Expression {

    @Override
    public long compute() {
        long leftValue = Expressions.get(left).compute();
        long rightValue = Expressions.get(right).compute();
        return switch (operation) {
            case ADD -> leftValue + rightValue;
            case SUBTRACT -> leftValue - rightValue;
            case MULTIPLY -> leftValue * rightValue;
            case DIVIDE -> leftValue / rightValue;
        };
    }

    @Override
    public long solve(long outcomeValue) {

        Optional<Long> optionalLeftValue = Expressions.get(left).computeIfPossible();
        Optional<Long> optionalRightValue = Expressions.get(right).computeIfPossible();

        if (optionalLeftValue.isPresent()) {
            long leftValue = optionalLeftValue.get();
            long newOutcome = switch (operation) {
                case ADD -> outcomeValue - leftValue;
                case SUBTRACT -> leftValue - outcomeValue;
                case MULTIPLY -> outcomeValue / leftValue;
                case DIVIDE -> leftValue / outcomeValue;
            };
            return Expressions.get(right).solve(newOutcome);
        } else if (optionalRightValue.isPresent()) {
            long rightValue = optionalRightValue.get();
            long newOutcome = switch (operation) {
                case ADD -> outcomeValue - rightValue;
                case SUBTRACT -> outcomeValue + rightValue;
                case MULTIPLY -> outcomeValue / rightValue;
                case DIVIDE -> outcomeValue * rightValue;
            };
            Expression leftExpression = Expressions.get(left);
            return leftExpression.solve(newOutcome);
        } else {
            throw new IllegalStateException("None of the operands of the expression can be computed");
        }
    }
}
