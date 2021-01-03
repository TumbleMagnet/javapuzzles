package be.rouget.puzzles.adventofcode.year2020.day19;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MonsterMessages {

    private static final String YEAR = "2020";
    private static final String DAY = "19";

    private static final Logger LOG = LogManager.getLogger(MonsterMessages.class);

    public static final int MAX_RECURSION = 100; // How many levels deep we go when generating a regexp for a recursive rule
    private final Map<Integer, Rule> rules = Maps.newHashMap();
    private final List<String> values = Lists.newArrayList();

    public MonsterMessages(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        for (String line : input) {
            if (line.contains(":")) {
                Rule.addRule(rules, line);
            } else if (isNotBlank(line)) {
                values.add(line);
            }
        }

        LOG.info("Parse {} rules and {} values...", rules.size(), values.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        MonsterMessages aoc = new MonsterMessages(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return countValuesMatchingRule0();
    }

    public long computeResultForPart2() {
        // Modify rules 8 and 11 and force to recompute all rules regexp
        alterRule8();
        alterRule11();
        for (Rule rule : rules.values()) {
            rule.clearPattern();
        }
        return countValuesMatchingRule0();
    }

    private long countValuesMatchingRule0() {
        Rule rule0 = rules.get(0);
        return values.stream()
                .filter(rule0::matches)
                .count();
    }

    private void alterRule11() {
        // Alter rule11
        //   Before: 11: 42 31
        //   After:  11: 42 31 | 42 11 31
        //   Consider this is equivalent to  42 31 | 42 42 31 31 | 42 42 42 31 31 31 | ...
        //      => 42{1} 31{1} | 42{2} 31{2} | ... (up to MAX_RECURSION times)
        String exp42 = rules.get(42).toRegularExpression();
        String exp31 = rules.get(31).toRegularExpression();
        String updatedExpression = "";
        for (int i = 1; i < MAX_RECURSION; i++) {
            if (isNotBlank(updatedExpression)) {
                updatedExpression += "|";
            }
            updatedExpression += "(" + exp42 +"){" + i + "}(" + exp31 + "){" + i + "}";
        }
        Rule rule11 = rules.get(11);
        rule11.setForcedRegularExpression("(" + updatedExpression + ")");
    }

    private void alterRule8() {
        // Alter rule8
        //   Before: 8: 42
        //   After:  8: 42 | 42 8
        //   => 42{1+}
        Rule rule8 = rules.get(8);
        String expression8 = rule8.toRegularExpression();
        String updatedExpression8 = "(" + expression8 + "){1,"+ MAX_RECURSION + "}";
        rule8.setForcedRegularExpression(updatedExpression8);
    }
}