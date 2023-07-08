package be.rouget.puzzles.adventofcode.year2022.day21;

public record ConstantExpression(int value) implements Expression {
    
    @Override
    public long compute() {
        return value;
    }

    @Override
    public long solve(long outcomeValue) {
        throw new IllegalStateException("Constant expression cannot be solved");
    }
}
