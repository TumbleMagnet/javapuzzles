package be.rouget.puzzles.adventofcode.year2020.day7;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2020.day2.AoC2020Day02;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HandyHaversacks {

    private static final String YEAR = "2020";
    private static final String DAY = "07";

    private static Logger LOG = LogManager.getLogger(HandyHaversacks.class);

    private List<String> input;
    private List<BagRule> rules;
    private Map<String, BagRule> rulesByColor;

    public HandyHaversacks(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
        rules = input.stream()
                .map(BagRule::fromInput)
                .collect(Collectors.toList());
        rulesByColor = rules.stream()
                .collect(Collectors.toMap(BagRule::getBagColor, Function.identity()));
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        HandyHaversacks aoc = new HandyHaversacks(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return bagsWhichContain("shiny gold").size();
    }

    private Set<String> bagsWhichContain(String color) {
        Set<String> bags = Sets.newHashSet();
        for (BagRule rule : rules) {
            if (rule.contains(color)) {
                bags.add(rule.getBagColor());
                bags.addAll(bagsWhichContain(rule.getBagColor()));
            }
        }
        return bags;
    }

    private BagRule findRule(String color) {
        return rulesByColor.get(color);
    }

    public long computeResultForPart2() {
        return bagsInsideColor("shiny gold");
    }

    private int bagsInsideColor(String color) {
        int count = 0;
        BagRule rule = findRule(color);
        for (BagRule.ColorCount colorCount : rule.getContents()) {
            count += colorCount.getCount();
            count += colorCount.getCount() * bagsInsideColor(colorCount.getColor());
        }
        return count;
    }
}