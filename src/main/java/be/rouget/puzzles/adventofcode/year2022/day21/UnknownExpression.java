package be.rouget.puzzles.adventofcode.year2022.day21;

public class UnknownExpression implements Expression {
    
    @Override
    public long compute() {
        throw new UnsupportedOperationException("Expression cannot be evaluated!");
    }

    @Override
    public long solve(long outcomeValue) {
        return outcomeValue;
    }
    
}
