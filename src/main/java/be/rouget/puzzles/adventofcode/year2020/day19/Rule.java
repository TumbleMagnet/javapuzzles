package be.rouget.puzzles.adventofcode.year2020.day19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Rule {

    private final int id;
    private final String text;
    private final Map<Integer, Rule> rules;

    private Pattern pattern;
    private String forcedRegularExpression;

    public Rule(int id, String text, Map<Integer, Rule> rules) {
        this.id = id;
        this.text = text;
        this.rules = rules;
    }

    public static void addRule(Map<Integer, Rule> rules, String input) {
        String[] tokens = input.split(": ");
        int ruleId = Integer.parseInt(tokens[0]);
        String ruleText = tokens[1];
        rules.put(ruleId, new Rule(ruleId, ruleText, rules));
    }

    public String toRegularExpression() {

        if (StringUtils.isNotBlank(forcedRegularExpression)) {
            return forcedRegularExpression;
        }

        // Rules can be:
        // - a simple char: "a"
        // - an list of other rules: 1 2 3
        // - an OR of list of rules: 1 2 | 2 1

        // Constants
        if (text.startsWith("\"")) {
            return text.replace("\"", "");
        }
        if (text.contains("|")) {
            String[] parts = text.split(" \\| ");
            return "(" + regexpForListOfRules(parts[0]) + "|" + regexpForListOfRules(parts[1]) + ")";
        }

        // List of rules
        return regexpForListOfRules(text);
    }

    private String regexpForListOfRules(String ruleText) {
        String[] tokens = ruleText.split(" ");
        return Arrays.stream(tokens).map(Integer::valueOf)
                .map(this::getRule)
                .map(Rule::toRegularExpression)
                .collect( Collectors.joining( "" ) );
    }

    private Rule getRule(Integer id) {
        Rule rule = rules.get(id);
        if (rule == null) {
            throw new IllegalArgumentException("Found no rule with id " + id);
        }
        return rule;
    }

    private Pattern getPattern() {
        if (pattern == null) {
            pattern = Pattern.compile(toRegularExpression());
        }
        return pattern;
    }

    public boolean matches(String input) {
        Matcher matcher = getPattern().matcher(input);
        return matcher.matches();
    }

    public void setForcedRegularExpression(String forcedRegularExpression) {
        this.forcedRegularExpression = forcedRegularExpression;
        this.pattern = null;
    }

    public void clearPattern() {
        this.pattern = null;
    }

    public String toString() {
        return id + ": " + text;
    }
}
