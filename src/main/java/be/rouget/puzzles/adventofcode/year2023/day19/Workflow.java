package be.rouget.puzzles.adventofcode.year2023.day19;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Workflow(String name, List<Rule> rules) {
    
    private static final Logger LOG = LogManager.getLogger(Aplenty.class);
    
    public static Workflow parse(String input) {
        
        // cq{a<289:R,a<499:A,m>826:A,A}
        Pattern pattern = Pattern.compile("(.*)\\{(.*)}");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        String name = matcher.group(1);
        List<Rule> rules = Arrays.stream(matcher.group(2).split(","))
                .map(Rule::parse)
                .toList();
        return new Workflow(name, rules);
    }
    
    public RuleOutcome evaluate(Part part) {
        RuleOutcome outcome = rules.stream()
                .filter(rule -> rule.matches(part))
                .findFirst()
                .map(Rule::outcome)
                .orElseThrow();
        LOG.info("Part {} for workflow {} -> {}", part, name, outcome);
        return outcome;
    }
}
