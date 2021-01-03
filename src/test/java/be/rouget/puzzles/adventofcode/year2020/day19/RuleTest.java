package be.rouget.puzzles.adventofcode.year2020.day19;

import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    void testRules() {

        Map<Integer, Rule> rules = Maps.newHashMap();

        Rule.addRule(rules, "4: \"a\"");
        Rule.addRule(rules, "5: \"b\"");
        Rule.addRule(rules, "11: 4 5");
        Rule.addRule(rules, "12: 4 5 | 5 4");

        assertThat(rules.get(4).matches("a")).isTrue();
        assertThat(rules.get(4).matches("b")).isFalse();
        assertThat(rules.get(4).matches("aa")).isFalse();

        assertThat(rules.get(5).matches("b")).isTrue();
        assertThat(rules.get(5).matches("a")).isFalse();
        assertThat(rules.get(5).matches("bb")).isFalse();

        assertThat(rules.get(11).matches("ab")).isTrue();
        assertThat(rules.get(11).matches("aa")).isFalse();
        assertThat(rules.get(11).matches("bb")).isFalse();
        assertThat(rules.get(11).matches("ba")).isFalse();

        assertThat(rules.get(12).matches("ab")).isTrue();
        assertThat(rules.get(12).matches("ba")).isTrue();
        assertThat(rules.get(12).matches("aa")).isFalse();
        assertThat(rules.get(12).matches("bb")).isFalse();
    }

}