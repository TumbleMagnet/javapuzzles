package be.rouget.puzzles.adventofcode.year2022.day21;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class MonkeyMath {

    private static final Logger LOG = LogManager.getLogger(MonkeyMath.class);

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(MonkeyMath.class);
        MonkeyMath aoc = new MonkeyMath(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public MonkeyMath(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        Map<String, Expression> expressions = input.stream()
                .map(MonkeyExpression::parse)
                .collect(Collectors.toMap(MonkeyExpression::monkeyName, MonkeyExpression::expression));
        Expressions.initialize(expressions);
    }

    public long computeResultForPart1() {
        return Expressions.get("root").compute();
    }

    public long computeResultForPart2() {

        Expressions.updateExpression("humn", new UnknownExpression());
        
        OperationExpression rootExpression = (OperationExpression) Expressions.get("root");

        // Find out which value of root expression can be computed and solve the other one
        Optional<Long> optionalLeft = Expressions.get(rootExpression.left()).computeIfPossible();
        Optional<Long> optionalRight = Expressions.get(rootExpression.right()).computeIfPossible();
        if (optionalLeft.isPresent()) {
            return Expressions.get(rootExpression.right()).solve(optionalLeft.get());
        } else if (optionalRight.isPresent()) {
            return Expressions.get(rootExpression.left()).solve(optionalRight.get());
        } else {
            throw new IllegalStateException("None of the operands of the expression for root can be computed");
        }
    }
}