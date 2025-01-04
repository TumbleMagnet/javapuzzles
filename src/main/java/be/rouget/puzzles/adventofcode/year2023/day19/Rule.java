package be.rouget.puzzles.adventofcode.year2023.day19;

public record Rule(RuleCondition condition, RuleOutcome outcome) {

    public boolean matches(Part part)  {
        return condition == null || condition.evaluate(part);
    }

    public static Rule parse(String input) {

        if (input.contains(":")) {
            String[] tokens = input.split(":");
            if (tokens.length != 2) {
                throw new IllegalArgumentException("Cannot parse rule: " + input);
            }
            RuleCondition condition = RuleCondition.parse(tokens[0]);
            RuleOutcome outcome = RuleOutcome.parse(tokens[1]);
            return new Rule(condition, outcome);
        } else {
            return new Rule(null, RuleOutcome.parse(input));
        }
    }
}
