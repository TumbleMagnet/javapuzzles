package be.rouget.puzzles.adventofcode.year2020.day2;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AoC2020Day02 {

    private static Logger LOG = LogManager.getLogger(AoC2020Day02.class);

    private List<String> input;

    public AoC2020Day02(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2020_day02_input.txt");
        AoC2020Day02 aoc = new AoC2020Day02(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        long count = input.stream()
                .map(Password::fromInput)
                .filter(Password::isValidPolicy1)
                .count();
        return count;
    }

    public long computeResultForPart2() {
        long count = input.stream()
                .map(Password::fromInput)
                .filter(Password::isValidPolicy2)
                .count();
        return count;
    }

    public static class Password {
        private String value;
        private Rule rule;

        public Password(String value, Rule rule) {
            this.value = value;
            this.rule = rule;
        }

        public String getValue() {
            return value;
        }

        public Rule getRule() {
            return rule;
        }

        public boolean isValidPolicy1() {
            int count = StringUtils.countMatches(getValue(), getRule().getCharacter());
            return count >= rule.getMin() && count <= rule.getMax();
        }

        public boolean isValidPolicy2() {
            try {
                char char1 = value.charAt(rule.getMin() - 1);
                char char2 = value.charAt(rule.getMax() - 1);
                char ruleChar = rule.getCharacter().charAt(0);
                return (char1 == ruleChar && char2 != ruleChar)
                        || (char1 != ruleChar && char2 == ruleChar);
            } catch (Exception e) {
                throw new RuntimeException("Error in isValid2 for " + this, e);
            }
        }

        @Override
        public String toString() {
            return "Password{" +
                    "value='" + value + '\'' +
                    ", rule=" + rule +
                    '}';
        }

        public static Password fromInput(String input) {

            Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Input " + input + " does not match regular expression");
            }
            String min = matcher.group(1);
            String max = matcher.group(2);
            String character = matcher.group(3);
            String value = matcher.group(4);

            Rule rule = new Rule(character, Integer.valueOf(min), Integer.valueOf(max));
            Password password = new Password(value, rule);
            return password;
        }
    }

    public static class Rule {

        private String character;
        private int min;
        private int max;

        public Rule(String character, int min, int max) {
            this.character = character;
            this.min = min;
            this.max = max;
        }

        public String getCharacter() {
            return character;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "character='" + character + '\'' +
                    ", min=" + min +
                    ", max=" + max +
                    '}';
        }
    }

}